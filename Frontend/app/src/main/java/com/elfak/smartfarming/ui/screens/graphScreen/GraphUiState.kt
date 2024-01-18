package com.elfak.smartfarming.ui.screens.graphScreen

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.ToastData
import com.elfak.smartfarming.domain.enums.GraphPeriods
import java.time.LocalDateTime

data class GraphUiState(
    val sensorId: String = "",
    val userEmail: String = "",
    val userId: String = "",
    val toastData: ToastData = ToastData(),
    val rule: Rule? = null,
    val sensor: Device = Device(),
    val actuator: Device? = null,
    val readings: List<GraphReading> = emptyList(),
    val graphPeriod: GraphPeriods = GraphPeriods.Months,
    val startDate: LocalDateTime = LocalDateTime.now().minusDays(1),
    val endDate: LocalDateTime =  LocalDateTime.now(),
)