package com.elfak.smartfarming.ui.screens.splashScreen

import androidx.lifecycle.ViewModel
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val localAuthRepository: ILocalAuthRepository
) : ViewModel() {
    suspend fun isLoggedIn(): Boolean {
        val credentials = localAuthRepository.getCredentials()
        return credentials.id.isNotEmpty()
    }


}