package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

import com.elfak.smartfarming.data.models.ToastData
import com.elfak.smartfarming.domain.enums.ScreenState

data class RuleDetailsUiState(
    val screenState: ScreenState = ScreenState.View,
    val data: ToastData = ToastData(),
)