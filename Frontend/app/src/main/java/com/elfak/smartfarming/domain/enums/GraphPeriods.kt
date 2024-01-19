package com.elfak.smartfarming.domain.enums

enum class GraphPeriods {
    Hours,
    Months,
    Years
}
fun GraphPeriods.forDisplay(): String {
    return when (this) {
        GraphPeriods.Hours -> "Hourly"
        GraphPeriods.Months -> "Monthly"
        GraphPeriods.Years -> "Yearly"
    }
}
