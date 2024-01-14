package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.enums.toScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RuleDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var uiState by mutableStateOf(RuleDetailsUiState())
        private set


    init {
        val state: String = savedStateHandle["screenState"]!!
        setScreenState(state.toScreenState())
        // fetch rule
    }

    private fun setScreenState(state: ScreenState) {
        uiState = uiState.copy(screenState = state)
    }

    fun loadData() {
        viewModelScope.launch {

        }
    }
}
