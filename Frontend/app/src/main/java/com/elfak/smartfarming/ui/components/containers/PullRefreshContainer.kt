@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.components.containers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PullRefreshContainer(
    refreshState: PullRefreshState,
    isRefreshing: Boolean,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.pullRefresh(refreshState)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}