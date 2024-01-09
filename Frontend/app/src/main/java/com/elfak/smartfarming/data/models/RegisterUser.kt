package com.elfak.smartfarming.data.models

data class RegisterUser(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)