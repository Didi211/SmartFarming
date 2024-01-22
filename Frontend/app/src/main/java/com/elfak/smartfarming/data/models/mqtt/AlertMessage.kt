package com.elfak.smartfarming.data.models.mqtt

data class AlertMessage(
    val message: String = "",
    val metadata: AlertMetadata = AlertMetadata(),
)