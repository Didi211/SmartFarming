package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

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
import com.elfak.smartfarming.data.models.api.RuleRequest
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.RuleExpressionType
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.enums.toScreenState
import com.elfak.smartfarming.domain.enums.toSignString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RuleDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localDeviceRepository: ILocalDeviceRepository,
    private val deviceRepository: IDeviceRepository,
    private val localAuthRepository: ILocalAuthRepository

): ViewModel() {
    var uiState by mutableStateOf(RuleDetailsUiState())
        private set
    private val _sensorLiveData: MutableLiveData<Device> by lazy {
        MutableLiveData<Device>(Device())
    }
    private val _actuatorLiveData: MutableLiveData<Device> by lazy {
        MutableLiveData<Device>(Device())
    }
    val sensorLiveData: LiveData<Device> = _sensorLiveData
    val actuatorLiveData: LiveData<Device> = _actuatorLiveData

    init {
        val state: String = savedStateHandle["screenState"]!!
        setScreenState(state.toScreenState())
        // fetch rule
        if (savedStateHandle.contains("ruleId")) {
            val ruleId: String = savedStateHandle["ruleId"]!!
            setRuleId(ruleId)
        }
        setRuleActions()

    }

    fun loadData() {
        try {
            viewModelScope.launch {
                if (uiState.ruleId != null) {
                    loadRule(uiState.ruleId!!)
                    loadRuleDevices()
                } else {
                    loadDevicesForSelect()
                }
            }
        }
        catch (ex: Exception) {
            handleError(ex)
            Log.e("Load Data", ex.message!!, ex)
        }
    }

    private suspend fun loadDevicesForSelect() {
        val userId = localAuthRepository.getCredentials().id
        val sensors = deviceRepository.getAvailableDevices(userId, DeviceTypes.Sensor)
        val actuators = deviceRepository.getAvailableDevices(userId, DeviceTypes.Actuator)
        uiState = uiState.copy(
            selectSensors = sensors,
            selectActuators = actuators
        )
    }

    private suspend fun loadRuleDevices() {
        val sensor = deviceRepository.getDeviceById(uiState.rule.sensorId)
        val actuator = deviceRepository.getDeviceById(uiState.rule.actuatorId)
        var localSensor = localDeviceRepository.getDevice(uiState.rule.sensorId)
        var localActuator = localDeviceRepository.getDevice(uiState.rule.actuatorId)
        if (localSensor == null) {
            localSensor = sensor
            localDeviceRepository.addDevice(localSensor)
        }
        if (localActuator == null) {
            localActuator = actuator
            localDeviceRepository.addDevice(localActuator)
        }
        viewModelScope.launch {
            localDeviceRepository.getDeviceAsFlow(uiState.rule.sensorId).collect {
                setSensor(it)
            }
        }
        viewModelScope.launch {
            localDeviceRepository.getDeviceAsFlow(uiState.rule.actuatorId).collect {
                setActuator(it)
            }
        }
    }
    fun setDeviceFromSelect(name: String, type: DeviceTypes) {
        when (type) {
            DeviceTypes.Sensor -> {
                val sensor = uiState.selectSensors.find { it.name == name }
                setSensor(sensor?: Device())
            }
            DeviceTypes.Actuator -> {
                val actuator = uiState.selectActuators.find { it.name == name }
                setActuator(actuator?: Device())
            }
        }

    }
    private suspend fun loadRule(ruleId: String) {
        val rule = deviceRepository.getRuleById(ruleId)
        setRule(rule)
    }

    private fun setRule(rule: Rule) {
        uiState = uiState.copy(rule = rule)
    }

    private fun setRuleActions() {
        val actions: MutableMap<String, (Any?) -> Unit> = mutableMapOf()
        actions["setName"] = { name: Any? -> setRuleName(name as String) }
        actions["setStartExpression"] = { expression: Any? -> setStartExpression(expression as RuleExpressionType) }
        actions["setStopExpression"] = { expression: Any? -> setStopExpression(expression as RuleExpressionType) }
        actions["setStartTriggerLevel"] = { level: Any? -> setStartTriggerLevel(level as String) }
        actions["setStopTriggerLevel"] = { level: Any? -> setStopTriggerLevel(level as String) }
//        actions["setSensor"] = { sensor: Any? -> setSensor(sensor as Device) }
//        actions["setActuator"] = { actuator: Any? -> setActuator(actuator as Device) }
        uiState = uiState.copy(
            ruleActions = actions.toMap()
        )
    }

    private fun setActuator(device: Device) {
        _actuatorLiveData.value = device
//        uiState = uiState.copy(
//            rule = uiState.rule.copy(actuatorId = device.id),
//            actuator = device
//        )
    }
    private fun setSensor(device: Device) {
        _sensorLiveData.value = device
//        uiState = uiState.copy(rule = uiState.rule.copy(sensorId = device.id),
//            sensor = device
//        )
    }

    private fun setStartTriggerLevel(level: String) {
        uiState = uiState.copy(rule = uiState.rule.copy(startTriggerLevel = level))

    }
    private fun setStopTriggerLevel(level: String) {
        uiState = uiState.copy(rule = uiState.rule.copy(stopTriggerLevel = level))
    }

    private fun setStopExpression(expression: RuleExpressionType) {
        uiState = uiState.copy(rule = uiState.rule.copy(stopExpression = expression))
    }

    private fun setStartExpression(expression: RuleExpressionType) {
        uiState = uiState.copy(rule = uiState.rule.copy(startExpression = expression))
    }


    private fun setRuleName(name: String) {
        uiState = uiState.copy(rule = uiState.rule.copy(name = name))
    }

    private fun setRuleId(ruleId: String) {
        uiState = uiState.copy(ruleId = ruleId)
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
    fun onDeviceBellIconClicked(id: String, type: DeviceTypes) {
        val device = toggleIsMuted(type)
        viewModelScope.launch {
            localDeviceRepository.setIsMuted(id, device.isMuted)
        }
        val message = if (device.isMuted) "muted" else "not muted"
        setSuccessMessage("Notifications for ${device.name} are $message.")
    }

    private fun toggleIsMuted(type: DeviceTypes): Device {
        return when (type) {
            DeviceTypes.Sensor -> {
                _sensorLiveData.value = _sensorLiveData.value?.copy(isMuted = !_sensorLiveData.value!!.isMuted)
                _sensorLiveData.value!!
    //                uiState.copy(sensor = uiState.sensor.copy(isMuted = !uiState.sensor.isMuted))
            }

            DeviceTypes.Actuator -> {
                _actuatorLiveData.value = _actuatorLiveData.value?.copy(isMuted = !_actuatorLiveData.value!!.isMuted)
                _actuatorLiveData.value!!
    //                uiState.copy(actuator = uiState.actuator!!.copy(isMuted = !uiState.actuator!!.isMuted))
            }
        }
//        return when (type) {
//            DeviceTypes.Sensor -> uiState.sensor
//            DeviceTypes.Actuator -> uiState.actuator!!
//        }
    }

    fun saveRule() {
        viewModelScope.launch {
            try {
                val user = localAuthRepository.getCredentials()
                val ruleRequest = RuleRequest(
                    id = uiState.rule.id,
                    name = uiState.rule.name,
                    userId = user.id,
                    sensorId = _sensorLiveData.value!!.id,
                    actuatorId = _actuatorLiveData.value!!.id,
                    startExpression = uiState.rule.startExpression.toSignString(),
                    stopExpression = uiState.rule.stopExpression.toSignString(),
                    startTriggerLevel = uiState.rule.startTriggerLevel.toInt(),
                    stopTriggerLevel = uiState.rule.stopTriggerLevel.toInt(),
                )
                val rule: Rule = if (uiState.screenState == ScreenState.Create) {
                    deviceRepository.addRule(ruleRequest, user.email)
                } else {
                    deviceRepository.updateRule(uiState.rule.id, ruleRequest, user.email)
                }
                setRule(rule)
                val action = if (uiState.screenState == ScreenState.Create) "created" else "edited"
                setSuccessMessage("Rule ${action}.")
                setScreenState(ScreenState.View)
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("${uiState.screenState.name} Rule", ex.message?: ex.toString(), ex)
            }

        }
    }

    fun deleteRule(onSuccess: () -> Unit = { }) {
        viewModelScope.launch {
            try {
                val email = localAuthRepository.getCredentials().email
                deviceRepository.removeRule(uiState.rule.id, email)
                setSuccessMessage("Rule removed")
                onSuccess()
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Delete Rule", ex.message?: ex.toString(), ex)
            }
        }
    }
}
