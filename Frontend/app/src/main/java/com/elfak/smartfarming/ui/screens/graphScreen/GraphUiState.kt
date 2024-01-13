package com.elfak.smartfarming.ui.screens.graphScreen

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.ToastData

data class GraphUiState(
    val sensorId: String = "",
    val userEmail: String = "",
    val toastData: ToastData = ToastData(),
    val rule: Rule? = null,
    val sensor: Device = Device(),
    val actuator: Device? = null,
)