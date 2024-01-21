package com.elfak.smartfarming.data.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class GraphReading(
    val timeMeasured: LocalDateTime = LocalDateTime.now(),
    val reading: Double = 0.0,
) {
    companion object {
        fun fromApiResponse(data: Any): GraphReading {
            val reading = data as Map<*, *>
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            return GraphReading(
                timeMeasured = LocalDateTime.parse(reading["timeMeasured"].toString(),formatter),
                reading = reading["reading"].toString().toDouble(),
            )
        }
    }
}
