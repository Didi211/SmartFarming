package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

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
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.enums.toScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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


    init {
        val state: String = savedStateHandle["screenState"]!!
        setScreenState(state.toScreenState())
        if (savedStateHandle.contains("deviceId")) {
            val deviceId: String = savedStateHandle["deviceId"]!!
            setDeviceId(deviceId)
        }
        viewModelScope.launch {
            try {
                if (uiState.deviceId != null) {
                    val deviceResult = async { deviceRepository.getDeviceById(uiState.deviceId!!) }
                    val ruleResult = async { deviceRepository.getRuleByDeviceId(uiState.deviceId!!)}
                    setDevice(deviceResult.await())
                    setRule(ruleResult.await())
                }
                val authResult = async { localAuthRepository.getCredentials().email }
                setUserEmail(authResult.await())
            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Init", ex.message!!, ex)
            }
        }
    }

    private fun setDevice(device: Device) {
        uiState = uiState.copy(device = device)
    }

    private fun setDeviceId(deviceId: String) {
        uiState = uiState.copy(deviceId = deviceId)
    }

    private fun setUserEmail(email: String) {
        uiState = uiState.copy(userEmail = email)
    }


    private fun setScreenState(state: ScreenState) {
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
}
