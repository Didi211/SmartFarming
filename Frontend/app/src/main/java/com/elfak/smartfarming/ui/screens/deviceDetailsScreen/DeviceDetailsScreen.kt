package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsScreenViewModel,
    navigateToRuleDetails: (ruleId: String) -> Unit,
) {
    Button(onClick = { navigateToRuleDetails("ruleId") }) {
        Text(text = "Go to rule details")
    }
}