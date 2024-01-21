package com.elfak.smartfarming.ui.screens.settingsScreen

data class SettingUiState(
    val isNotificationEnabled: Boolean = false,
    val isServiceEnabled: Boolean = false,
    val isRealTimeUpdatesEnabled: Boolean = false,
)