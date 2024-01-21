@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.screens.notificationScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import com.elfak.smartfarming.ui.components.ComposableLifecycle
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.containers.NotificationCardContainer
import com.elfak.smartfarming.ui.components.containers.PullRefreshContainer
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun NotificationScreen(
    viewModel: NotificationScreenViewModel
) {
    ComposableLifecycle { _, event ->
        when(event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.refreshNotifications()
            }
            else -> {}
        }
    }

    val refreshState = rememberPullRefreshState(
        refreshing = viewModel.uiState.isRefreshing,
        onRefresh = { viewModel.refreshNotifications() }
    )

    // error handler
    ToastHandler(
        toastData = viewModel.uiState.toastData,
        clearErrorMessage = viewModel::clearErrorMessage,
        clearSuccessMessage = viewModel::clearSuccessMessage
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AnimatedContent(targetState = viewModel.uiState.notifications.isNotEmpty(), label = "",
        ) { state ->
            when(state) {
                true -> {
                    NotificationCardContainer(
                        notifications = viewModel.uiState.notifications,
                        refreshState = refreshState,
                        isRefreshing = viewModel.uiState.isRefreshing,
                        onDelete = viewModel::deleteNotification
                    )
                }
                false -> {
                    PullRefreshContainer(
                        refreshState = refreshState,
                        isRefreshing = viewModel.uiState.isRefreshing
                    ) {
                        Text(
                            "No notifications.",
                            style = MaterialTheme.typography.headlineMedium,
                            color = FontColor,
                        )
                    }
                }
            }
            
        }
    }
}

