package com.elfak.smartfarming.ui.screens.welcomeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun WelcomeScreen(
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    Column {
        Button(onClick = navigateToLogin) {
            Text(text = "Go to Login Screen")
        }
        Button(onClick = navigateToRegister) {
            Text(text = "Go to Register Screen")
        }
    }

}