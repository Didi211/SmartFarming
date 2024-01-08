package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RuleDetailsScreen(
    viewModel: RuleDetailsScreenViewModel,
    navigateToDeviceDetails: (deviceId: String) -> Unit,
) {
    Button(onClick = {
        navigateToDeviceDetails("deviceId")
    }) {
        Text(text = "Go to device details")
    }
}