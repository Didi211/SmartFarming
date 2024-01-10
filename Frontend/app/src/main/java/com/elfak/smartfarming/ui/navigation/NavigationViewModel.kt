package com.elfak.smartfarming.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.data.repositories.interfaces.ILocalAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val localAuthRepository: ILocalAuthRepository
): ViewModel(){
    fun signOut() {
        viewModelScope.launch {
            localAuthRepository.removeCredentials()
        }
    }
}