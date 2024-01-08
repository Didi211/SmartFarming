package com.elfak.smartfarming.ui.screens.addRuleScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AddRuleScreen(
    viewModel: AddRuleScreenViewModel,
    navigateBack: () -> Unit,
    navigateToRuleDetails: (ruleId: String) -> Unit,
) {
    Column {
        Button(onClick = { navigateToRuleDetails("ruleId") }) {
            Text(text = "Go to rule details")
        }
        Button(onClick = navigateBack) {
            Text(text = "Go back")
        }
    }
}