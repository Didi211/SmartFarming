package com.elfak.smartfarming.ui.screens.homeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.repositories.interfaces.IDeviceRepository
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.domain.enums.DeviceTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val localAuthRepository: ILocalAuthRepository,
    private val deviceRepository: IDeviceRepository

) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set


    fun getSensors(){
        viewModelScope.launch {
            setRefreshingFlag(true)
            try {
                val user = localAuthRepository.getCredentials()
                setSensors(deviceRepository.getAllDevices(user.id, DeviceTypes.Sensor))
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Sensors-GET", ex.message!!, ex)
            }
            finally {
                setRefreshingFlag(false)
            }
        }
    }

    private fun setSensors(devices: List<Device>) {
        uiState = uiState.copy(sensors = devices)
    }

    private fun setRefreshingFlag(value: Boolean) {
        uiState = uiState.copy(isRefreshing = value)
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
}