package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elfak.smartfarming.domain.enums.ScreenState

@Composable
fun RuleDetailsScreen(
    viewModel: RuleDetailsScreenViewModel,
    navigateBack: () -> Unit = { },
    navigateToDeviceDetails: (deviceId: String, screenState: ScreenState) -> Unit,
) {
    Column {
        Text(text = viewModel.uiState.screenState.toString())
        Button(onClick = { navigateToDeviceDetails("deviceId", ScreenState.View) }) {
            Text(text = "Go to device details")
        }
    }
}