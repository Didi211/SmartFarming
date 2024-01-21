package com.elfak.smartfarming.data.models

import com.elfak.smartfarming.domain.enums.RuleExpressionType
import com.elfak.smartfarming.domain.enums.toRuleExpressionType

data class Rule(
    val id: String = "",
    val name: String = "",
    val sensorId: String = "",
    val actuatorId: String = "",
    val startExpression: RuleExpressionType = RuleExpressionType.Smaller,
    val stopExpression: RuleExpressionType = RuleExpressionType.Larger,
    val startTriggerLevel: String = "",
    val stopTriggerLevel: String = "",
    val description: String = "",
) {
    companion object {
        fun fromApiResponse(data: Any): Rule {
            val rule = data as Map<*, *>

            return Rule(
                id = rule["id"].toString(),
                name =  rule["name"].toString(),
                sensorId = rule["sensorId"].toString(),
                actuatorId = rule["actuatorId"].toString(),
                startExpression = rule["startExpression"].toString().toRuleExpressionType(),
                stopExpression = rule["stopExpression"].toString().toRuleExpressionType(),
                startTriggerLevel = rule["startTriggerLevel"].toString().toDouble().toInt().toString(),
                stopTriggerLevel = rule["stopTriggerLevel"].toString().toDouble().toInt().toString(),
                description = rule["text"].toString(),
            )
        }
    }
}

