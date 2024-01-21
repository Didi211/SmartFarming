package com.elfak.smartfarming.ui.screens.settingsScreen

import com.elfak.smartfarming.data.models.ToastData

data class SettingUiState(
    val isNotificationEnabled: Boolean = false,
    val isServiceEnabled: Boolean = false,
    val isRealTimeUpdatesEnabled: Boolean = false,
    val toastData: ToastData = ToastData(),

    )