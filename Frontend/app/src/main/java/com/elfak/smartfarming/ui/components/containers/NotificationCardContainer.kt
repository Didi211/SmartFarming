@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.components.containers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Notification
import com.elfak.smartfarming.ui.components.cards.NotificationCard

@Composable
fun NotificationCardContainer(
    notifications: List<Notification>,
    refreshState: PullRefreshState,
    isRefreshing: Boolean,
    onDelete: (notificationId: String) -> Unit = { },
) {
    Box(modifier = Modifier.pullRefresh(refreshState)) {
       LazyColumn(
           Modifier.fillMaxSize(),
           verticalArrangement = Arrangement.spacedBy(8.dp),
           contentPadding = PaddingValues(15.dp)
       ) {
           items (notifications) { notification ->
                NotificationCard(notification = notification, onDelete = onDelete)
           }
       }
        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}