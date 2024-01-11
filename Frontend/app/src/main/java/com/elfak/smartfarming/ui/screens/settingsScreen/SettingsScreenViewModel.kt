package com.elfak.smartfarming.ui.screens.settingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.elfak.smartfarming.ui.screens.loginScreen.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(): ViewModel() {
    fun toggleNotificationSound(value: Boolean) {
        uiState = uiState.copy(isNotificationEnabled = value)
    }

    var uiState by mutableStateOf(SettingUiState())
        private set
}
