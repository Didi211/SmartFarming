package com.elfak.smartfarming.ui.screens.settingsScreen

import android.app.ActivityManager
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(): ViewModel() {

    var uiState by mutableStateOf(SettingUiState(isRealTimeUpdatesEnabled = false))
        private set

    fun toggleNotificationSound(value: Boolean) {
        uiState = uiState.copy(isNotificationEnabled = value)
    }

    fun toggleService(value: Boolean) {
        uiState = uiState.copy(isServiceEnabled = value)
    }

    fun toggleRealTimeUpdate(value: Boolean) {
        uiState = uiState.copy(isRealTimeUpdatesEnabled = value)
    }

    fun isServiceRunning(context: Context): Boolean {
//        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val runningServices = activityManager.getRunningServices(Int.MAX_VALUE)
//
//        for (serviceInfo in runningServices) {
//            if (LocationTrackingService::class.java.name == serviceInfo.service.className) {
//                return true
//            }
//        }

        return false
    }


}
