package com.elfak.smartfarming.ui.screens.loginScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import com.elfak.smartfarming.data.repositories.interfaces.IRemoteAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val localAuthRepository: ILocalAuthRepository,
    private val remoteAuthRepository: IRemoteAuthRepository
): ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun setEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun setPassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    suspend fun login(onSuccess: () -> Unit) {
        try {
            val user = remoteAuthRepository.login(uiState.email, uiState.password)
            localAuthRepository.setCredentials(user)
            onSuccess()
        }
        catch (ex: Exception) {
            handleError(ex)
            Log.e("Login-Error", ex.message!!, ex)
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


}