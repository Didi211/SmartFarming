package com.elfak.smartfarming.domain.enums

enum class DeviceState {
    On,
    Off
}

fun DeviceState.uppercase(): String {
    return this.name.uppercase()
}
fun DeviceState.lowercase(): String {
    return this.name.lowercase()
}
fun String.toDeviceState(): DeviceState? {
    if (this == null) return null
    return when (this.uppercase()) {
        "OFF" -> DeviceState.Off
        "ON" -> DeviceState.On
        "NULL" -> null
        else -> { throw Exception("Device State not valid.")}
    }
}


