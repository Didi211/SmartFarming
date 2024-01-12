package com.elfak.smartfarming.data.models

import com.elfak.smartfarming.domain.enums.DeviceState
import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.toDeviceState
import com.elfak.smartfarming.domain.enums.toDeviceStatus
import com.elfak.smartfarming.domain.enums.toDeviceType


data class Device(
    var id: String = "",
    var name: String = "",
    var type: DeviceTypes = DeviceTypes.Sensor,
    var status: DeviceStatus = DeviceStatus.Online,
    var unit: String? = null,
    var state: DeviceState? = null,
    var isMuted: Boolean = false,
) {
    companion object {
        fun fromApiResponse(data: Any): Device {
            val device = data as Map<*, *>

            return Device(
                id = device["id"].toString(),
                name =  device["name"].toString(),
                type = device["type"].toString().toDeviceType(),
                status = device["status"].toString().toDeviceStatus(),
                unit = device["unit"] as String?,
                state = device["state"].toString().toDeviceState(),
            )
        }
    }
}
