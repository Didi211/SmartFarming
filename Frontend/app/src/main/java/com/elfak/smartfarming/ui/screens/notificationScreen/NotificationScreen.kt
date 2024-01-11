@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.screens.notificationScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.containers.NotificationCardContainer
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun NotificationScreen(
    viewModel: NotificationScreenViewModel
) {
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
                    Box(modifier = Modifier.pullRefresh(refreshState)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 15.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                "No notifications.",
                                style = MaterialTheme.typography.headlineMedium,
                                color = FontColor,
                            )
                        }
                        PullRefreshIndicator(viewModel.uiState.isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
                    }
                }
            }
            
        }
    }
}