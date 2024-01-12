@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.components.containers

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun DeviceTab(
    devices: List<Device>,
    refreshState: PullRefreshState,
    isRefreshing: Boolean,
    onBellIconClick: (id: String) -> Unit = { },
    onCardClick: (deviceId: String, editMode: Boolean?) -> Unit = { _, _ -> },
    onDelete: (id: String) -> Unit = { },
    onEdit: (id: String, editMode: Boolean?) -> Unit = { _, _ -> },
) {
    AnimatedContent(devices.isEmpty(), label = "") { empty ->
        when (empty) {
            true -> {
                PullRefreshContainer(
                    refreshState = refreshState,
                    isRefreshing = isRefreshing
                ) {
                    Text(
                        "No devices.",
                        style = MaterialTheme.typography.headlineMedium,
                        color = FontColor,
                    )
                }
            }
            false -> {
                DeviceCardContainer(
                    devices = devices,
                    refreshState = refreshState,
                    isRefreshing = isRefreshing,
                    onDelete = onDelete,
                    onEdit = onEdit,
                    onBellIconClick = onBellIconClick,
                    onCardClick = onCardClick
                )
            }
        }
    }
}