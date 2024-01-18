package com.elfak.smartfarming.ui.screens.homeScreen

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.data.models.ToastData

data class HomeUiState(
    val dataLoaded: Boolean = false,
    val isRefreshing: Boolean = false,
    val sensors: List<Device> = emptyList(),
    val readings: MutableMap<String, List<GraphReading>> = mutableMapOf(),
    val toastData: ToastData = ToastData(),
)