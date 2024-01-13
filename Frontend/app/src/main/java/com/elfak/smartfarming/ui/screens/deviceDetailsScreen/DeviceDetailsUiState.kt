package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.ToastData
import com.elfak.smartfarming.domain.enums.ScreenState

data class DeviceDetailsUiState(
    val screenState: ScreenState = ScreenState.View,
    val toastData: ToastData = ToastData(),
    val userEmail: String = "",
    val rule: Rule? = null,
    val device: Device? = null,
    val deviceId: String? = null,
)
