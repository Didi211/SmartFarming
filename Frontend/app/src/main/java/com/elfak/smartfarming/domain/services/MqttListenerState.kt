package com.elfak.smartfarming.domain.services

import com.elfak.smartfarming.data.models.User

data class MqttListenerState(
    val isRealTimeDataEnabled: Boolean = false,
//    val sensorId: String? = null, // null means listen for every sensor,
    val isNotificationSoundEnabled: Boolean = false,
    val user: User = User("", "", "", ""),
    val isSubscribedToAlerts: Boolean = false,
    val isSubscribedToRtData: Boolean = false
)
