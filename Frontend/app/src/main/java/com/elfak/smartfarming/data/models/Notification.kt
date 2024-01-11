package com.elfak.smartfarming.data.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



data class Notification(
    val id: String = "",
    val message: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val isRead: Boolean = false,
    val deviceStatus: String = "",
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

    companion object {
        fun fromApiResponse(data: Any): Notification {
            val notification = data as Map<*, *>
            return Notification(
                id = notification["id"].toString(),
                message =  notification["message"].toString(),
                createdAt = notification["createdAt"].toString(),
                updatedAt = notification["updatedAt"].toString(),
                isRead = notification["isRead"] as Boolean,
                deviceStatus = notification["deviceStatus"].toString(),



            )
        }
    }
}


fun String.formatDate(): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy")
    val dateTime = LocalDateTime.parse(this.toString(), inputFormatter)
    return dateTime.format(outputFormatter)
}