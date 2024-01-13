package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elfak.smartfarming.domain.enums.ScreenState

@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsScreenViewModel,
    navigateBack: () -> Unit = { },
    navigateToRuleDetails: (ruleId: String?, screenState: ScreenState) -> Unit,
) {
    Column {
        Text(text = viewModel.uiState.screenState.toString())
        Button(onClick = { navigateToRuleDetails("ruleId", ScreenState.View) }) {
            Text(text = "Go to rule details")
        }
    }
}