package com.elfak.smartfarming.data.models.api

data class ApiResponse (
    val status: Int,
    val message: String,
    val details: Any? = null
)