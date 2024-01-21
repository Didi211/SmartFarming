package com.elfak.smartfarming.ui.screens.graphScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.api.GraphDataRequest
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalDeviceRepository
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.GraphPeriods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
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
    init {
        val sensorId: String = savedStateHandle["sensorId"]!!
        uiState = uiState.copy(sensorId = sensorId)
    }


    private fun setUserEmail(email: String) {
        uiState = uiState.copy(userEmail = email)
    }


    fun loadData() {
        try {
            viewModelScope.launch {
                loadUser()
                if (uiState.sensorId.isBlank()) {
                    throw Exception("Cannot load data. Sensor ID is not found.")
                }
                loadSensor(uiState.sensorId)
                loadSensorReadings(uiState.sensorId)
                loadRule(uiState.sensorId)
                if (uiState.rule != null) {
                    loadActuator(uiState.rule!!.actuatorId)
                }
            }
        }
        catch (ex: Exception) {
            handleError(ex)
            Log.e("Load Data", ex.message!!, ex)
        }
    }
    private suspend fun loadRule(sensorId: String) {
        val rule = deviceRepository.getRuleByDeviceId(sensorId)
        setRule(rule)
    }
    private suspend fun loadSensor(id: String) {
        val sensor = deviceRepository.getDeviceById(id)
        var localSensor = localDeviceRepository.getDevice(id)

        if (localSensor == null) {
            localSensor = sensor
            localDeviceRepository.addDevice(localSensor)
        } else {
            // update local device
            localSensor = sensor.copy(
                isMuted = localSensor.isMuted,
                lastReading = localSensor.lastReading
            )
            localDeviceRepository.updateDeviceLocal(localSensor)
        }
        setSensor(localSensor)


    }

    fun refreshGraph() {
        viewModelScope.launch {
            try {
                loadSensorReadings(uiState.sensorId)
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Readings-Get", ex.message!!, ex)

            }
        }
    }
    private suspend fun loadSensorReadings(sensorId: String) {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val startDate: String
        val endDate: String
        when(uiState.graphPeriod) {
            GraphPeriods.Hours -> {
                startDate = uiState.startDate.truncatedTo(ChronoUnit.HOURS).format(dateTimeFormatter)
                endDate = uiState.endDate.truncatedTo(ChronoUnit.HOURS).format(dateTimeFormatter)
            }
            GraphPeriods.Months -> {
                startDate = uiState.startDate.truncatedTo(ChronoUnit.DAYS).format(dateTimeFormatter)
                endDate = uiState.endDate.truncatedTo(ChronoUnit.DAYS).format(dateTimeFormatter)
            }
            GraphPeriods.Years ->  {
                startDate = uiState.startDate.truncatedTo(ChronoUnit.DAYS).withDayOfMonth(1).format(dateTimeFormatter)
                endDate = uiState.endDate.truncatedTo(ChronoUnit.DAYS).withDayOfMonth(1).format(dateTimeFormatter)
            }
        }
        val readings = deviceRepository.getGraphData(
            sensorId = sensorId,
            userId = uiState.userId,
            period = uiState.graphPeriod,
            graphDataRequest = GraphDataRequest(
                startDate = startDate,
                endDate = endDate
        ))
        if (readings.isEmpty()) {
            setErrorMessage("No data found for required period.")
        }
        setGraphReadings(readings)
    }
    private fun setGraphReadings(readings: List<GraphReading>) {
        uiState = uiState.copy(readings = readings)
    }

    fun setGraphPeriod(period: GraphPeriods) {
        uiState = uiState.copy(graphPeriod = period)
    }
    fun setDates(startDate: LocalDateTime = LocalDateTime.now(), endDate: LocalDateTime = LocalDateTime.now(), isChosen: Boolean) {
        uiState = uiState.copy(startDate = startDate, endDate = endDate, isPeriodChosen = isChosen)
    }

    private suspend fun loadUser() {
        val user = localAuthRepository.getCredentials()
        setUserEmail(user.email)
        setUserId(user.id)
    }

    private fun setUserId(id: String) {
        uiState = uiState.copy(userId = id)
    }

    private suspend fun loadActuator(id: String) {
        val actuator = deviceRepository.getDeviceById(id)
        var localActuator = localDeviceRepository.getDevice(actuator.id)
        if (localActuator == null) {
            localDeviceRepository.addDevice(actuator)
            localActuator = actuator
        } else {
            // update local device
            localActuator = actuator.copy(
                isMuted = localActuator.isMuted,
                lastReading = localActuator.lastReading
            )
            localDeviceRepository.updateDeviceLocal(localActuator)
        }
        setActuator(localActuator)
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
                deviceRepository.removeDevice(id, uiState.userEmail, uiState.userId)
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
