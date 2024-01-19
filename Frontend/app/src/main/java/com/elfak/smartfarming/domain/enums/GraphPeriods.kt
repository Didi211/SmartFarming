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

fun String.toGraphPeriod(): GraphPeriods {
    return when(this.uppercase()) {
        "HOURS" -> GraphPeriods.Hours
        "MONTHS" -> GraphPeriods.Months
        "YEARS" -> GraphPeriods.Years
        else -> { throw Exception("Graph period not valid.")}

    }
}
