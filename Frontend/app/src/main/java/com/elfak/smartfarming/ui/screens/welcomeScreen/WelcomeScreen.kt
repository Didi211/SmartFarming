package com.elfak.smartfarming.ui.screens.welcomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.ui.components.buttons.BasicButton
import com.elfak.smartfarming.ui.components.images.LogoImage
import com.elfak.smartfarming.ui.theme.SmartFarmingTheme

@Composable
fun WelcomeScreen(
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(bottom = 150.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            //Logo Component
            LogoImage(size = 330.dp)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BasicButton(
                    text = "LOGIN",
                    onClick = navigateToLogin)
                Spacer(modifier = Modifier.height(15.dp))
                BasicButton(
                    text = "REGISTER",
                    onClick = navigateToRegister)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun WelcomeScreenPreview() {
//    SmartFarmingTheme {
//        WelcomeScreen(navigateToLogin = { /*TODO*/ }) {
//
//        }
//    }
//}