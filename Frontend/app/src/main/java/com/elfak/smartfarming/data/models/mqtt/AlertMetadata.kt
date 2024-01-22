package com.elfak.smartfarming.data.models.mqtt

import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.domain.enums.toDeviceStatus

data class AlertMetadata(
    val deviceId: String = "",
    val status: String = "",
)