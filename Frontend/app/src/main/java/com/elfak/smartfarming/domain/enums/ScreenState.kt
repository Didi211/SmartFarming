package com.elfak.smartfarming.domain.enums

enum class ScreenState {
    Edit,
    View,
    Create
}

fun String.toScreenState(): ScreenState {
    return when (this.uppercase()) {
        "EDIT" -> ScreenState.Edit
        "VIEW" -> ScreenState.View
        "CREATE" -> ScreenState.Create
        else -> { throw Exception("Screen state $this not valid.")}
    }
}
