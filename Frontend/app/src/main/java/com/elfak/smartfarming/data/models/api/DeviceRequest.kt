package com.elfak.smartfarming.data.models.api

data class DeviceRequest(
    val id: String = "",
    val name: String = "",
    val userId: String = "",
    val type: String = "",
    val status: String = "",
    val state: String? = null,
    val unit: String? = null,
)
