package com.elfak.smartfarming.ui.screens.listScreen

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.ToastData
import com.elfak.smartfarming.domain.utils.Tabs

data class ListScreenUiState(
    val tabSelected: Tabs = Tabs.Devices,
    val devices: List<Device> = emptyList(),
    val isRefreshing: Boolean = false,
    val rules: List<Rule> = emptyList(),

    val toastData: ToastData = ToastData(),
)