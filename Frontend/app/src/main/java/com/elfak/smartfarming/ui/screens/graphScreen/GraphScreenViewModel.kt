package com.elfak.smartfarming.ui.screens.graphScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.domain.enums.DeviceTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deviceRepository: IDeviceRepository,
    private val localAuthRepository: ILocalAuthRepository,
    private val localDeviceRepository: ILocalDeviceRepository
): ViewModel() {
    var uiState by mutableStateOf(GraphUiState())
        private set

    private fun setUserEmail(email: String) {
        uiState = uiState.copy(userEmail = email)
    }

    init {
        val sensorId: String = savedStateHandle["sensorId"]!!
        uiState = uiState.copy(sensorId = sensorId)
        // fetch devices
        viewModelScope.launch {
            try {
                val sensorResult = async { deviceRepository.getDeviceById(sensorId) }
                val ruleResult = async { deviceRepository.getRuleByDeviceId(sensorId)}
                val authResult = async { localAuthRepository.getCredentials().email }
                val results = awaitAll(sensorResult,ruleResult, authResult)
                var sensor = localDeviceRepository.getDevice((results[0] as Device).id)
                if (sensor == null) {
                    sensor = results[0] as Device
                    localDeviceRepository.addDevice(sensor)
                }
                setSensor(sensor)
                setRule(results[1] as Rule?)
                setUserEmail(results[2] as String)

                if (uiState.rule != null) {
                    val actuator = deviceRepository.getDeviceById(uiState.rule!!.actuatorId)
                    var localActuator = localDeviceRepository.getDevice(actuator.id)
                    if (localActuator == null) {
                        localDeviceRepository.addDevice(actuator)
                        localActuator = actuator
                    }
                    setActuator(localActuator)
                }
                // fetch graph readings
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Init", ex.message!!, ex)
            }
        }
    }

    private fun setActuator(actuator: Device?) {
        uiState = uiState.copy(actuator = actuator)
    }

    private fun setRule(rule: Rule?) {
        uiState = uiState.copy(rule = rule)

    }

    private fun setSensor(sensor: Device) {
        uiState = uiState.copy(sensor = sensor)
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
    fun deleteDevice(id: String, type: DeviceTypes, onSuccess: () -> Unit = { }) {
        viewModelScope.launch {
            try {
                deviceRepository.removeDevice(id, uiState.userEmail)
                if (type == DeviceTypes.Sensor) {
                    setSuccessMessage("Device removed")
                    onSuccess()
                }
                else {
                    setActuator(null)
                    setSuccessMessage("Device removed")
                }
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Device-DELETE", ex.message!!, ex)
            }
        }
    }

    fun deleteRule(ruleId: String) {
        viewModelScope.launch {
            try {
                deviceRepository.removeRule(ruleId, uiState.userEmail)
                setRule(null)
                setActuator(null)
                setSuccessMessage("Rule removed")
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Rule-DELETE", ex.message!!, ex)
            }
        }
    }

    fun onDeviceBellIconClicked(id: String, type: DeviceTypes) {
        val device = toggleIsMuted(type)
        viewModelScope.launch {
            localDeviceRepository.setIsMuted(id, device.isMuted)
        }
        val message = if (device.isMuted) "muted" else "not muted"
        setSuccessMessage("Notifications for ${device.name} are $message.")
    }

    private fun toggleIsMuted(type: DeviceTypes): Device {
        uiState = when (type) {
            DeviceTypes.Sensor -> {
                uiState.copy(sensor = uiState.sensor.copy(isMuted = !uiState.sensor.isMuted))
            }

            DeviceTypes.Actuator -> {
                uiState.copy(actuator = uiState.actuator!!.copy(isMuted = !uiState.actuator!!.isMuted))
            }
        }
        return when (type) {
            DeviceTypes.Sensor -> uiState.sensor
            DeviceTypes.Actuator -> uiState.actuator!!
        }
    }
}
