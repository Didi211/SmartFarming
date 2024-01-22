package com.elfak.smartfarming.ui.screens.settingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.data.repositories.interfaces.ISettingsRepository
import com.elfak.smartfarming.domain.utils.ServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val settingsRepository: ISettingsRepository,
    private val serviceManager: ServiceManager
): ViewModel() {

    var uiState by mutableStateOf(SettingUiState())
        private set

    fun toggleNotificationSound(value: Boolean) {
        uiState = uiState.copy(isNotificationEnabled = value)
        viewModelScope.launch {
            settingsRepository.setNotificationSoundSetting(value)
        }
    }

    fun toggleService(enabled: Boolean) {
        if (enabled) serviceManager.startService() else serviceManager.stopService()
        setEnabledService(enabled)
        val state = if (enabled) "started" else "stopped"
        setSuccessMessage("Service $state.")
    }

    fun toggleRealTimeUpdate(value: Boolean) {
        uiState = uiState.copy(isRealTimeUpdatesEnabled = value)
        viewModelScope.launch {
            settingsRepository.setRealTimeDataSetting(value)
        }
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
    private fun setSuccessMessage(message: String) {
        uiState = uiState.copy(toastData = uiState.toastData.copy(successMessage = message, hasSuccessMessage = true))
    }
    fun clearSuccessMessage() {
        uiState = uiState.copy(toastData = uiState.toastData.copy(hasSuccessMessage = false))
    }
    // endregion

    private fun setEnabledService(enabled: Boolean) {
        uiState = uiState.copy(isServiceEnabled = enabled)
    }

    fun prepareData() {
        val isRunning = serviceManager.isServiceRunning()
        setEnabledService(isRunning)
        viewModelScope.launch {
            val realTimeSettingEnabled = settingsRepository.getRealTimeSetting()
            val notificationSoundSettingEnabled = settingsRepository.getSoundSetting()
            toggleNotificationSound(notificationSoundSettingEnabled?: false)
            toggleRealTimeUpdate(realTimeSettingEnabled?: false)

        }

    }
}
