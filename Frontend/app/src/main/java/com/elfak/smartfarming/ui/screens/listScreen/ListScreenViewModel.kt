package com.elfak.smartfarming.ui.screens.listScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.domain.utils.Tabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val deviceRepository: IDeviceRepository,
    private val localAuthRepository: ILocalAuthRepository,
    private val localDeviceRepository: ILocalDeviceRepository
): ViewModel() {

    var uiState by mutableStateOf(ListScreenUiState())
        private set
    private val _devicesLiveData: MutableLiveData<List<Device>> by lazy {
        MutableLiveData<List<Device>>()
    }
    val deviceLiveData: LiveData<List<Device>> = _devicesLiveData

    private fun setSelectedTab(tab: Tabs) {
        uiState = uiState.copy(tabSelected = tab)
    }

    fun toggleTab() {
        val tab = when(uiState.tabSelected) {
            Tabs.Devices -> Tabs.Rules
            Tabs.Rules -> Tabs.Devices
        }
        setSelectedTab(tab)
        refreshList()
    }

    fun refreshList() {
        when (uiState.tabSelected) { 
            Tabs.Devices -> getDevices()
            Tabs.Rules -> getRules()
        }
    }

    private fun getDevices() {
        viewModelScope.launch {
            setRefreshingFlag(true)
            try {
                val userId = localAuthRepository.getCredentials().id
                val devices = deviceRepository.getAllDevices(userId)

                viewModelScope.launch {
                    try {
                        localDeviceRepository.updateDevicesLocal(devices).catch { flowEx ->
                            throw flowEx
                        }.collect {
                             _devicesLiveData.value = it
                        }
                    }
                    catch (ex:Exception) {
                        handleError(ex)
                        Log.e("Devices-GET", ex.message!!, ex)
                    }
                }
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Devices-GET", ex.message!!, ex)
            }
            finally {
                setRefreshingFlag(false)
            }
        }
    }

    private fun getRules() {
        viewModelScope.launch {
            setRefreshingFlag(true)
            try {
                val userId = localAuthRepository.getCredentials().id
                val rules = deviceRepository.getAllRules(userId)
                setRules(rules)
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Rules-GET", ex.message!!, ex)
            }
            finally {
                setRefreshingFlag(false)
            }
        }
    }

    private fun setRules(rules: List<Rule>) {
        uiState = uiState.copy(rules = rules)
    }

    private fun setRefreshingFlag(value: Boolean) {
        uiState = uiState.copy(isRefreshing = value)
    }

    fun onDeviceDelete(id: String) {
        viewModelScope.launch {
            try {
                val user = localAuthRepository.getCredentials()
                deviceRepository.removeDevice(id, user.email, user.id)
                removeDevice(id)
                setSuccessMessage("Device removed")
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Devices-DELETE", ex.message!!, ex)
            }
        }
    }

    private fun removeDevice(id: String) {
        _devicesLiveData.value = _devicesLiveData.value?.filter { it.id != id }
        uiState = uiState.copy(devices = uiState.devices.filter { it.id != id })
    }

    fun onDeviceBellIconClick(id: String) {
        val device = toggleIsMutedDevice(id)
        val message = if (device.isMuted) "muted" else "not muted"
        setSuccessMessage("Notifications for ${device.name} are $message.")
        // save setting in local
        viewModelScope.launch {
            localDeviceRepository.setIsMuted(device.id, device.isMuted)
        }
    }

    private fun toggleIsMutedDevice(id: String): Device {
        var updatedDevice = Device()
        val devices = _devicesLiveData.value?.map {
            if (it.id == id) {
                updatedDevice = it.copy(isMuted = !it.isMuted)
                updatedDevice
            } else it
        }
        _devicesLiveData.value = devices
//        uiState = uiState.copy(devices = devices)
        return updatedDevice
//        val devices = uiState.devices.map {
//            if (it.id == id) {
//                updatedDevice = it.copy(isMuted = !it.isMuted)
//                updatedDevice
//            }
//            else it
//        }
//        uiState = uiState.copy(devices = devices)
//        return updatedDevice
    }
    fun onRuleDelete(id: String) {
        viewModelScope.launch {
            try {
                val user = localAuthRepository.getCredentials()
                deviceRepository.removeRule(id, user.email)
                removeRule(id)
                setSuccessMessage("Rule removed")
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Rules-DELETE", ex.message!!, ex)
            }
        }
    }

    private fun removeRule(id: String) {
        uiState = uiState.copy(rules = uiState.rules.filter { it.id != id })
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
    private fun setErrorMessage(message: String) {
        uiState = uiState.copy(toastData = uiState.toastData.copy(errorMessage = message, hasErrors = true))
    }
    private fun setSuccessMessage(message: String) {
        uiState = uiState.copy(toastData = uiState.toastData.copy(successMessage = message, hasSuccessMessage = true))
    }
    fun clearSuccessMessage() {
        uiState = uiState.copy(toastData = uiState.toastData.copy(hasSuccessMessage = false))
    }
    // endregion

}
