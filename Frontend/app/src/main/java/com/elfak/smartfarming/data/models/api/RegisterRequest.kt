package com.elfak.smartfarming.data.models.api

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
)