package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsScreenViewModel,
    navigateToRuleDetails: (ruleId: String) -> Unit,
) {
    Column {
        Text(text = viewModel.uiState.screenState.toString())
        Button(onClick = { navigateToRuleDetails("ruleId") }) {
            Text(text = "Go to rule details")
        }
    }
}