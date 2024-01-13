@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.screens.homeScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.containers.GraphCardContainer
import com.elfak.smartfarming.ui.components.containers.PullRefreshContainer
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navigateToGraphReadings: (sensorId: String) -> Unit,
) {
    val refreshState = rememberPullRefreshState(
        refreshing = viewModel.uiState.isRefreshing,
        onRefresh = { viewModel.getSensors() }
    )

    ToastHandler(
        toastData = viewModel.uiState.toastData,
        clearErrorMessage = viewModel::clearErrorMessage,
        clearSuccessMessage = viewModel::clearSuccessMessage
    )
    AnimatedContent(viewModel.uiState.sensors.isEmpty(), label = "") { empty ->
        when (empty) {
            true -> PullRefreshContainer(
                refreshState = refreshState,
                isRefreshing = viewModel.uiState.isRefreshing
            ) {
                Text(
                    "No sensors.",
                    style = MaterialTheme.typography.headlineMedium,
                    color = FontColor,
                )
            }
            false -> {
                GraphCardContainer(
                    sensors = viewModel.uiState.sensors,
                    refreshState = refreshState,
                    isRefreshing = viewModel.uiState.isRefreshing,
                    onCardClick = navigateToGraphReadings
                )
            }
        }
    }
}