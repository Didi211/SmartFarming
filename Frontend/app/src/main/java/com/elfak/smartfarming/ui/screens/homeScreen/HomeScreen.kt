package com.elfak.smartfarming.ui.screens.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navigateToGraphReadings: (sensorId: String) -> Unit,
) {
    Column {
        Button(onClick = {
            navigateToGraphReadings("sensorId")
        }) {
            Text(text = "Go to graph readings")
        }
    }
}