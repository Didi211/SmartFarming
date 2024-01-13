package com.elfak.smartfarming.domain.enums

enum class RuleExpressionType {
    Larger,
    Smaller
}

fun String.toRuleExpressionType(): RuleExpressionType {
    return when (this.uppercase()) {
        ">" -> RuleExpressionType.Larger
        "<" -> RuleExpressionType.Smaller
        else -> { throw Exception("Rule expression type not valid.")}
    }
}