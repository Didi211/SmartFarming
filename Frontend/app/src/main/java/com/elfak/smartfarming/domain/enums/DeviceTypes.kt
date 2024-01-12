package com.elfak.smartfarming.domain.enums

enum class DeviceTypes {
    Sensor,
    Actuator,

}
fun DeviceTypes.uppercase(): String {
    return this.name.uppercase()
}

fun DeviceTypes.lowercase(): String {
    return this.name.lowercase()
}

fun String.toDeviceType(): DeviceTypes {
    return when (this.uppercase()) {
        "SENSOR" -> DeviceTypes.Sensor
        "ACTUATOR" -> DeviceTypes.Actuator
        else -> { throw Exception("Device Type not valid.")}
    }
}
