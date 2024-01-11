@file:Suppress("DEPRECATION")

package com.elfak.smartfarming.ui.screens.notificationScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.data.models.Notification
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.INotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewModel @Inject constructor(
    private val notificationRepository: INotificationRepository,
    private val localAuthRepository: ILocalAuthRepository
): ViewModel() {
    var uiState by mutableStateOf(NotificationUiState())
        private set

    init {
        viewModelScope.launch {
           getNotifications()
        }
    }

    private suspend fun getNotifications() {
        try {
            val userId = localAuthRepository.getCredentials().id
            val notifications = notificationRepository.getAll(userId)
            setNotifications(notifications)
        }
        catch (ex: Exception) {
            handleError(ex)
            Log.e("Notifications-GET", ex.message!!, ex)
        }
    }

    private fun setNotifications(notifications: List<Notification>) {
        uiState = uiState.copy(notifications = notifications)
    }
    private fun removeNotification(id: String) {
        uiState = uiState.copy(notifications = uiState.notifications.filter { it.id != id })
    }
    fun deleteNotification(id: String) {
        viewModelScope.launch {
            try {
                notificationRepository.remove(id)
                removeNotification(id)
                setSuccessMessage("Notification removed.")

            }
            catch (ex: Exception) {
                handleError(ex)
                Log.e("Notifications-DELETE", ex.message!!, ex)
            }
        }
    }

    // region TOAST HANDLER
    private fun handleError(ex: Exception) {
        if (ex.message != null) {
            setErrorMessage(ex.message!!)
            return
        }
        setErrorMessage("Error has occurred")
    }

    fun clearErrorMessage() {
        uiState = uiState.copy(toastData = uiState.toastData.copy(hasErrors = false))
    }
    fun setErrorMessage(message: String) {
        uiState = uiState.copy(toastData = uiState.toastData.copy(errorMessage = message, hasErrors = true))
    }
    fun setSuccessMessage(message: String) {
        uiState = uiState.copy(toastData = uiState.toastData.copy(successMessage = message, hasSuccessMessage = true))
    }
    fun clearSuccessMessage() {
        uiState = uiState.copy(toastData = uiState.toastData.copy(hasSuccessMessage = false))
    }
    // endregion
    private fun setRefreshingFlag(value: Boolean) {
        uiState = uiState.copy(isRefreshing = value)
    }

    fun refreshNotifications() {
        viewModelScope.launch {
            setRefreshingFlag(true)
            delay(500)
            getNotifications()
            setRefreshingFlag(false)
        }
    }

}
