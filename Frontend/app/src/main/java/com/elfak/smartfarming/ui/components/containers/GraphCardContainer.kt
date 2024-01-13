@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.components.containers

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Device

@Composable
fun GraphCardContainer(
    sensors: List<Device>,
    refreshState: PullRefreshState,
    isRefreshing: Boolean,
    onCardClick: (deviceId: String) -> Unit = { },
) {
    Box(modifier = Modifier.pullRefresh(refreshState)) {
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(sensors) { sensor ->
                GraphCard(
                    sensor = sensor,
                    onCardClick = onCardClick
                )
            }
        }
        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))

    }
}

@Composable
fun GraphCard(sensor: Device, onCardClick: (deviceId: String) -> Unit) {
    Column(
        Modifier
            .border(2.dp, Color.Black)
            .padding(10.dp)
            .height(200.dp)
            .fillMaxWidth(0.9f)
            .clickable { onCardClick(sensor.id) },
        ) {
        Text(
            text = sensor.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Column(Modifier.fillMaxSize().border(2.dp, Color.Black)) { }
    }
}