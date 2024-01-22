package com.elfak.smartfarming.domain.services

data class MqttListenerState(
    val isListeningRTData: Boolean = false,
    val sensorId: String? = null, // null means listen for every sensor,
    val isNotificationSoundEnabled: Boolean = false,
)
