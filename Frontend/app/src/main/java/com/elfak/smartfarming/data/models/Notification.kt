package com.elfak.smartfarming.data.models

import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.domain.enums.toDeviceStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class Notification(
    val id: String = "",
    val message: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val isRead: Boolean = false,
    val deviceStatus: DeviceStatus = DeviceStatus.Offline,
) {
    fun title(): String? {
        val startIndex = this.message.indexOf('[')
        val endIndex = this.message.indexOf(']')

        // Check if both '[' and ']' are present and in the correct order
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return this.message.substring(startIndex + 1, endIndex)
        }
        return null
    }
    fun noDeviceName(): String {
        if (this.message.isBlank()) return ""
        return this.message.replace(Regex(" \\[.*?]"), "")
    }

    companion object {
        fun fromApiResponse(data: Any): Notification {
            val notification = data as Map<*, *>
            return Notification(
                id = notification["id"].toString(),
                message =  notification["message"].toString(),
                createdAt = notification["createdAt"].toString(),
                updatedAt = notification["updatedAt"].toString(),
                isRead = notification["isRead"] as Boolean,
                deviceStatus = notification["deviceStatus"].toString().toDeviceStatus()
            )
        }
    }
}


fun String.formatDate(): String {
    if (this.isBlank()) return ""
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy")
    val dateTime = LocalDateTime.parse(this, inputFormatter)
    return dateTime.format(outputFormatter)
}
fun String.formatShortDate(): String {
    if (this.isBlank()) return ""
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm-dd/MM")
    val dateTime = LocalDateTime.parse(this, inputFormatter)
    return dateTime.format(outputFormatter)
}

