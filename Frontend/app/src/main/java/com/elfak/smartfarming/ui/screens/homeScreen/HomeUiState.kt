package com.elfak.smartfarming.ui.screens.homeScreen

import com.elfak.smartfarming.data.models.ToastData

data class HomeUiState(
    val dataLoaded: Boolean = false,

    val toastData: ToastData = ToastData(),
    )