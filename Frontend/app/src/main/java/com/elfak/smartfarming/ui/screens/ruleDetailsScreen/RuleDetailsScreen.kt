package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RuleDetailsScreen(
    viewModel: RuleDetailsScreenViewModel,
    navigateToDeviceDetails: (deviceId: String) -> Unit,
) {
    Column {
        Text(text = viewModel.uiState.screenState.toString())
        Button(onClick = { navigateToDeviceDetails("deviceId") }) {
            Text(text = "Go to device details")
        }
    }
}