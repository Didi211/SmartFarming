package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.enums.toScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var uiState by mutableStateOf(DeviceDetailsUiState())
        private set
    init {
        val state: String = savedStateHandle["screenState"]!!
        setScreenState(state.toScreenState())
        // fetch device
    }

    private fun setScreenState(state: ScreenState) {
        uiState = uiState.copy(screenState = state)
    }
}
