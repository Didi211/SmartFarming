package com.elfak.smartfarming.ui.screens.loginScreen

import com.elfak.smartfarming.data.models.ToastData

data class LoginUiState(
    var email: String = "",
    var password: String = "",

    val toastData: ToastData = ToastData(),
) {

}