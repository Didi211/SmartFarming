package com.elfak.smartfarming.domain.enums

enum class DeviceStatus {
    Online,
    Offline
}
fun DeviceStatus.uppercase(): String {
    return this.name.uppercase()
}

fun DeviceStatus.lowercase(): String {
    return this.name.lowercase()
}

fun String.toDeviceStatus(): DeviceStatus {
    return when (this.uppercase()) {
        "ONLINE" -> DeviceStatus.Online
        "OFFLINE" -> DeviceStatus.Offline
        else -> { throw Exception("Device Status not valid.")}
    }
}
