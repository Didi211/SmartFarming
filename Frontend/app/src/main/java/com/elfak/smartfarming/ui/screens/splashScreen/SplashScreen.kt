package com.elfak.smartfarming.ui.screens.splashScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.ui.components.images.HandPlantImage
import com.elfak.smartfarming.ui.components.images.LogoImage
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel,
    navigateToHome: () -> Unit,
    navigateToWelcome: () -> Unit,
) {
    val scaleLogo = remember { Animatable(0.0f) }

    //Animation
    LaunchedEffect(key1 = true) {
        scaleLogo.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(650, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )

        delay(650L)
        if (viewModel.isLoggedIn()) {
            navigateToHome()
        }
        else {
            navigateToWelcome()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(bottom = 150.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            //Logo Component
            LogoImage(size = 330.dp, modifier = Modifier.scale(scaleLogo.value))
        }
        HandPlantImage(Modifier
            .fillMaxWidth(0.7f)
            .align(Alignment.BottomStart)
            .padding(bottom = 30.dp))
    }
}

//@Preview(
//    showBackground = true
//)
//@Composable
//fun ScreenPreview() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .padding(bottom = 150.dp)
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//
//        ) {
//            //Logo Component
//            LogoImage(size = 370.dp)
//        }
//        HandPlantImage(Modifier
//            .fillMaxWidth(0.7f)
//            .align(Alignment.BottomStart)
//            .padding(bottom = 30.dp))
//    }
//}

