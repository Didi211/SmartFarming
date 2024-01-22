package com.elfak.smartfarming.data.models.mqtt

data class RealTimeData(
    val sensorId: String = "",
    val reading: Double = 0.0,
)