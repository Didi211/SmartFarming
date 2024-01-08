package com.elfak.smartfarming.ui.screens.splashScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel,
    navigateToHome: () -> Unit,
    navigateToWelcome: () -> Unit,
) {
    Column {
        Button(onClick = navigateToWelcome) {
            Text(text = "Navigate to welcome")
        }
        Button(onClick = navigateToHome) {
            Text(text = "Navigate to Home")
        }

    }
}