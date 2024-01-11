package com.elfak.smartfarming.ui.screens.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elfak.smartfarming.ui.components.ToastHandler

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navigateToGraphReadings: (sensorId: String) -> Unit,
) {

    ToastHandler(
        toastData = viewModel.uiState.toastData,
        clearErrorMessage = viewModel::clearErrorMessage,
        clearSuccessMessage = viewModel::clearSuccessMessage
    )

    Column {
        Button(onClick = {
            navigateToGraphReadings("sensorId")
        }) {
            Text(text = "Go to graph readings")
        }
    }
}