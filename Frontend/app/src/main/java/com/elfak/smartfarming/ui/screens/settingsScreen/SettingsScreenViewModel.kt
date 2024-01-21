package com.elfak.smartfarming.ui.screens.settingsScreen

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.elfak.smartfarming.domain.services.MqttListenerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(): ViewModel() {

    var uiState by mutableStateOf(SettingUiState())
        private set

    fun toggleNotificationSound(value: Boolean) {
        uiState = uiState.copy(isNotificationEnabled = value)
    }

    fun toggleService(enabled: Boolean, context: Context) {
        val command = if (enabled) MqttListenerService.ACTION_START else MqttListenerService.ACTION_STOP
        val intent = Intent(context, MqttListenerService::class.java).apply {
            action = command
        }
        context.startForegroundService(intent)
        setEnabledService(enabled)
        val state = if (enabled) "started" else "stopped"
        setSuccessMessage("Service $state.")
    }

    fun toggleRealTimeUpdate(value: Boolean) {
        uiState = uiState.copy(isRealTimeUpdatesEnabled = value)
    }

    @Suppress("DEPRECATION")
    fun isServiceRunning(context: Context): Boolean {
        return (context.getSystemService(ACTIVITY_SERVICE) as ActivityManager)
            .getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == MqttListenerService::class.java.name }
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

    fun setEnabledService(enabled: Boolean) {
        uiState = uiState.copy(isServiceEnabled = enabled)
    }


}
