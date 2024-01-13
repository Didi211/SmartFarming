package com.elfak.smartfarming.ui.screens.graphScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elfak.smartfarming.domain.enums.ScreenState

@Composable
fun GraphScreen(
    viewModel: GraphScreenViewModel,
    navigateBack: () -> Unit = { },
    navigateToDeviceDetails: (deviceId: String, screenState: ScreenState) -> Unit,
    navigateToRuleDetails: (ruleId: String?, screenState: ScreenState) -> Unit,
) {
    Column {
        Button(onClick = { navigateToDeviceDetails("deviceId", ScreenState.View) }) {
            Text(text = "Go to device details")
        }
        Button(onClick = { navigateToRuleDetails(null, ScreenState.Create) }) {
            Text(text = "Go to rule details")
        }
    }
}