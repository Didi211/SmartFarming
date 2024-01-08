package com.elfak.smartfarming.ui.screens.loginScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit
) {
    Column {
        Button(onClick = navigateToHome) {
            Text(text = "Go to Home Screen")
        }
        Button(onClick = navigateBack) {
            Text(text = "Go Back")
        }
    }
}