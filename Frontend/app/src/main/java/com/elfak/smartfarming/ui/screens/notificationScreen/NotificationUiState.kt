package com.elfak.smartfarming.ui.screens.notificationScreen

import com.elfak.smartfarming.data.models.Notification
import com.elfak.smartfarming.data.models.ToastData

data class NotificationUiState(
    val notifications: List<Notification> = emptyList(),
    val toastData: ToastData = ToastData(),
    val isRefreshing: Boolean  = false,


    )