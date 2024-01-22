package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.api.DeviceRequest
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.domain.enums.DeviceState
import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.enums.toScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deviceRepository: IDeviceRepository,
    private val localAuthRepository: ILocalAuthRepository,
    private val localDeviceRepository: ILocalDeviceRepository
): ViewModel() {

    var uiState by mutableStateOf(DeviceDetailsUiState())
        private set
    private val _deviceLiveData: MutableLiveData<Device?> by lazy {
        MutableLiveData<Device?>(Device())
    }
    val deviceLiveData: LiveData<Device?> = _deviceLiveData

    init {
        val state: String = savedStateHandle["screenState"]!!
        setScreenState(state.toScreenState())
        if (savedStateHandle.contains("deviceId")) {
            val deviceId: String = savedStateHandle["deviceId"]!!
            setDeviceId(deviceId)
        }
        setDeviceActions()
    }

    private suspend fun loadRule(deviceId: String) {
        setRule(deviceRepository.getRuleByDeviceId(deviceId))
    }
    private suspend fun loadUser() {
        val user = localAuthRepository.getCredentials()
        setUserEmail(user.email)
        setUserId(user.id)
    }
    private suspend fun loadDevice(id: String) {
        val device = deviceRepository.getDeviceById(id)
        var localDevice = localDeviceRepository.getDevice(id)
        if (localDevice == null) {
            localDevice = device
            localDeviceRepository.addDevice(localDevice)
        } else {
            // update local device
            localDevice = device.copy(
                isMuted = localDevice.isMuted,
                lastReading = localDevice.lastReading,
                lastReadingTime = localDevice.lastReadingTime
            )
            localDeviceRepository.updateDeviceLocal(localDevice)
        }
        viewModelScope.launch {
            localDeviceRepository.getDeviceAsFlow(id).collect {
                setDevice(it)
            }
        }
    }

    fun loadData() {
        try {
            viewModelScope.launch {
                loadUser()
                if (uiState.deviceId != null) {
                    loadDevice(uiState.deviceId!!)
                    loadRule(uiState.deviceId!!)
                }
            }
        }
        catch (ex: Exception) {
            handleError(ex)
            Log.e("Load Data", ex.message!!, ex)
        }
    }

    private fun setUserId(id: String) {
        uiState = uiState.copy(userId = id)
    }

    private fun setDeviceActions() {
        val actions: MutableMap<String, (Any?) -> Unit> = mutableMapOf()
        actions["setName"] = { name: Any? -> setDeviceName(name as String) }
        actions["setType"] = { type: Any? -> setDeviceType(type as DeviceTypes) }
        actions["setStatus"] = { status: Any? -> setDeviceStatus(status as DeviceStatus) }
        actions["setUnit"] = { unit: Any? -> setDeviceUnit(unit as String?) }
        actions["setState"] = { state: Any? -> setDeviceState(state as DeviceState?) }
//        actions["setLastReading"] = { reading: Any? -> setDeviceLastReading(reading as Double) }
        uiState = uiState.copy(
            deviceActions = actions.toMap()
        )
    }

    // region Device Actions
    private fun setDeviceName(name: String) {
        _deviceLiveData.value = _deviceLiveData.value?.copy(name = name)
//        uiState = uiState.copy(device = uiState.device.copy(name = name))
    }
    private fun setDeviceType(type: DeviceTypes) {
        _deviceLiveData.value = _deviceLiveData.value?.copy(type = type)
//        uiState = uiState.copy(device = uiState.device.copy(type = type))
    }
    private fun setDeviceStatus(status: DeviceStatus) {
        _deviceLiveData.value = _deviceLiveData.value?.copy(status = status)
//        uiState = uiState.copy(device = uiState.device.copy(status = status))
    }
    private fun setDeviceUnit(unit: String?) {
        _deviceLiveData.value = _deviceLiveData.value?.copy(unit = unit)
//        uiState = uiState.copy(device = uiState.device.copy(unit = unit))
    }
    private fun setDeviceState(state: DeviceState?) {
        _deviceLiveData.value = _deviceLiveData.value?.copy(state = state)
//        uiState = uiState.copy(device = uiState.device.copy(state = state))
    }

    // endregion

    private fun setDevice(device: Device) {
        _deviceLiveData.value = device
//        uiState = uiState.copy(device = device)
    }

    private fun setDeviceId(deviceId: String) {
        uiState = uiState.copy(deviceId = deviceId)
    }

    private fun setUserEmail(email: String) {
        uiState = uiState.copy(userEmail = email)
    }


    fun setScreenState(state: ScreenState) {
        uiState = uiState.copy(screenState = state)
    }

    // region TOAST HANDLER
    private fun handleError(ex: Exception) {
        if (ex.message != null) {
            setErrorMessage(ex.message!!)
            return
        }
        setErrorMessage("Error has occurred")
    }

    fun clearErrorMessage() {
        uiState = uiState.copy(toastData = uiState.toastData.copy(hasErrors = false))
    }
    fun setErrorMessage(message: String) {
        uiState = uiState.copy(toastData = uiState.toastData.copy(errorMessage = message, hasErrors = true))
    }
    fun setSuccessMessage(message: String) {
        uiState = uiState.copy(toastData = uiState.toastData.copy(successMessage = message, hasSuccessMessage = true))
    }
    fun clearSuccessMessage() {
        uiState = uiState.copy(toastData = uiState.toastData.copy(hasSuccessMessage = false))
    }


    // endregion
    fun deleteRule(ruleId: String) {
        viewModelScope.launch {
            try {
                deviceRepository.removeRule(ruleId, uiState.userEmail)
                setRule(null)
                setSuccessMessage("Rule removed")
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Rule-DELETE", ex.message!!, ex)
            }
        }
    }

    private fun setRule(rule: Rule?) {
        uiState = uiState.copy(rule = rule)

    }

    fun deleteDevice(onSuccess: () -> Unit = { }) {
        viewModelScope.launch {
            try {
                deviceRepository.removeDevice(_deviceLiveData.value!!.id, uiState.userEmail, uiState.userId)
                localDeviceRepository.removeDevice(_deviceLiveData.value!!.id)
                setSuccessMessage("Device removed")
                onSuccess()
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Device-DELETE", ex.message!!, ex)
            }
        }
    }

    fun onDeviceBellIconClicked() {
        toggleIsMuted()
        val isMuted = if (_deviceLiveData.value!!.isMuted) "muted" else "not muted"
        if (uiState.screenState != ScreenState.Create) {
            viewModelScope.launch {
                localDeviceRepository.setIsMuted(_deviceLiveData.value!!.id, _deviceLiveData.value!!.isMuted)
            }
            setSuccessMessage("Notifications for ${_deviceLiveData.value!!.name} are $isMuted.")
        }
        else {
            setSuccessMessage("Notifications for this device will be $isMuted.")
        }
    }

    private fun toggleIsMuted() {
        _deviceLiveData.value = _deviceLiveData.value?.copy(isMuted = !_deviceLiveData.value!!.isMuted)
//        uiState = uiState.copy(device = uiState.device.copy(isMuted = !uiState.device.isMuted))
    }

    fun saveDevice() {
        viewModelScope.launch {
            try {
                val deviceRequest = DeviceRequest(
                    id = _deviceLiveData.value!!.id,
                    name = _deviceLiveData.value!!.name,
                    userId = uiState.userId,
                    type = _deviceLiveData.value!!.type.name,
                    status = _deviceLiveData.value!!.status.name,
                    state = _deviceLiveData.value!!.state?.name,
                    unit = _deviceLiveData.value!!.unit
                )
                val device: Device = if (uiState.screenState == ScreenState.Create) {
                    deviceRepository.addDevice(deviceRequest, uiState.userEmail, uiState.userId)
                } else { // else can only be Edit
                    deviceRepository.updateDevice(deviceRequest, uiState.userEmail)
                }
                device.isMuted = _deviceLiveData.value!!.isMuted
                device.lastReading = _deviceLiveData.value!!.lastReading
                device.lastReadingTime = _deviceLiveData.value!!.lastReadingTime
                setDevice(device)
                setDeviceId(device.id)
                localDeviceRepository.addDevice(device)
                val action = if (uiState.screenState == ScreenState.Create) "created" else "edited"
                setSuccessMessage("Device ${action}.")
                setScreenState(ScreenState.View)
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("${uiState.screenState.name} Device", ex.message?: ex.toString(), ex)
            }
        }
    }
}
