package com.elfak.smartfarming.ui.screens.registerScreen

import com.elfak.smartfarming.data.models.ToastData

data class RegisterUiState(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    val name: String = "",

    val toastData: ToastData = ToastData(),
)