@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.screens.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.utils.Tabs
import com.elfak.smartfarming.ui.components.ComposableLifecycle
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.buttons.TabSwitch
import com.elfak.smartfarming.ui.components.containers.DeviceTab
import com.elfak.smartfarming.ui.components.containers.RuleTab

@Composable
fun ListScreen(
    viewModel: ListScreenViewModel,
    navigateToDeviceDetails: (deviceId: String, screenState: ScreenState) -> Unit,
    navigateToRuleDetails: (ruleId: String, screenState: ScreenState) -> Unit
) {
    ComposableLifecycle { _, event ->
        when(event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.refreshList()
            }
            else -> {}
        }
    }

    val refreshState = rememberPullRefreshState(
        refreshing = viewModel.uiState.isRefreshing,
        onRefresh = { viewModel.refreshList() }
    )

    ToastHandler(
        toastData = viewModel.uiState.toastData,
        clearErrorMessage = viewModel::clearErrorMessage,
        clearSuccessMessage = viewModel::clearSuccessMessage
    )

    Column(
        Modifier
            .fillMaxSize()
//            .padding(horizontal = 15.dp, vertical = 10.dp),
            .padding(start = 15.dp, end = 15.dp, top = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // tab switch
        TabSwitch(leftTabTitle = Tabs.Devices.name, rightTabTitle = Tabs.Rules.name, activeTab = viewModel.uiState.tabSelected.name) {
            viewModel.toggleTab()
        }
        Spacer(Modifier.height(50.dp))  
        
        when(viewModel.uiState.tabSelected) {
            Tabs.Devices -> {
                DeviceTab(
                    devices = viewModel.uiState.devices,
                    refreshState = refreshState,
                    isRefreshing = viewModel.uiState.isRefreshing,
                    onDelete = viewModel::onDeviceDelete,
                    onEdit = navigateToDeviceDetails,
                    onBellIconClick = viewModel::onDeviceBellIconClick,
                    onCardClick = navigateToDeviceDetails
                )
            }
            Tabs.Rules -> {
                RuleTab(
                    rules = viewModel.uiState.rules,
                    refreshState = refreshState,
                    isRefreshing = viewModel.uiState.isRefreshing,
                    onCardClick = navigateToRuleDetails,
                    onEdit = navigateToRuleDetails,
                    onDelete = viewModel::onRuleDelete
                )
            }
        }
    }
}