package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.elfak.smartfarming.domain.enums.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RuleDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var uiState by mutableStateOf(RuleDetailsUiState())
        private set

    init {
        if (savedStateHandle.contains("editMode")) {
            val editModeString: String = savedStateHandle["editMode"]!!
            val editMode = editModeString.toBoolean()
            setScreenState(if (editMode) ScreenState.Edit else ScreenState.View)
        }
        val ruleId: String = savedStateHandle["ruleId"]!!
        // fetch rule
    }

    private fun setScreenState(state: ScreenState) {
        uiState = uiState.copy(screenState = state)
    }
}
