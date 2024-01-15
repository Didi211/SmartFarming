package com.elfak.smartfarming.data.models.api

data class RuleRequest(
    val id: String = "",
    val name: String = "",
    val userId: String = "",
    val sensorId: String = "",
    val actuatorId: String = "",
    val startExpression: String = "",
    val stopExpression: String = "",
    val startTriggerLevel: Int = 0,
    val stopTriggerLevel: Int = 0
)
