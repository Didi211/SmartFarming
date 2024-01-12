@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.components.containers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.ui.components.cards.DeviceCard

@Composable
fun DeviceCardContainer(
    devices: List<Device>,
    refreshState: PullRefreshState,
    isRefreshing: Boolean,
    onBellIconClick: (id: String) -> Unit = { },
    onCardClick: (deviceId: String, editMode: Boolean?) -> Unit = { _, _ -> },
    onDelete: (id: String) -> Unit = { },
    onEdit: (id: String, editMode: Boolean?) -> Unit = { _, _ -> },
) {
    Box(modifier = Modifier.pullRefresh(refreshState)) {
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(15.dp)
        ) {
            items (devices) { device ->
                DeviceCard(
                    device = device,
                    menuItems = prepareMenuItems(device.id, onEdit, onDelete),
                    onBellIconClick = { onBellIconClick(device.id) },
                    onCardClick = { onCardClick(device.id, null) },
                )
            }
        }
        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}

private fun prepareMenuItems(
    deviceId: String,
    onEdit: (id: String, editMode: Boolean) -> Unit,
    onDelete: (id: String) -> Unit
): List<MenuItem> {
    return listOf(
        MenuItem("Edit", Icons.Rounded.Edit, action = { onEdit(deviceId, true) }),
        MenuItem("Delete", Icons.Rounded.Delete, action = { onDelete(deviceId) })
    )
}