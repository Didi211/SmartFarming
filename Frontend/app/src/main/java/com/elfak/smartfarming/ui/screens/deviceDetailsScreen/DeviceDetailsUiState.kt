package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import com.elfak.smartfarming.data.models.ToastData
import com.elfak.smartfarming.domain.enums.ScreenState

data class DeviceDetailsUiState(
    val screenState: ScreenState = ScreenState.View,
    val data: ToastData = ToastData(),
)
