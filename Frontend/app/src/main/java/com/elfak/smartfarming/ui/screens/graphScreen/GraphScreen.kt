package com.elfak.smartfarming.ui.screens.graphScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GraphScreen(
    viewModel: GraphScreenViewModel,
    navigateToDeviceDetails: (deviceId: String, editMode: Boolean?) -> Unit,
    navigateToRuleDetails: (ruleId: String, editMode: Boolean?) -> Unit,
) {
    Column {
        Button(onClick = { navigateToDeviceDetails("deviceId", true) }) {
            Text(text = "Go to device details")
        }
        Button(onClick = { navigateToRuleDetails("ruleId", true) }) {
            Text(text = "Go to rule details")
        }
    }
}