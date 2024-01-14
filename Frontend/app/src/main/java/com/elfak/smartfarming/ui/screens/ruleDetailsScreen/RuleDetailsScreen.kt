package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.ui.components.ComposableLifecycle

@Composable
fun RuleDetailsScreen(
    viewModel: RuleDetailsScreenViewModel,
    navigateBack: () -> Unit = { },
    navigateToDeviceDetails: (deviceId: String, screenState: ScreenState) -> Unit,
) {
    ComposableLifecycle { _, event ->
        when(event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.loadData()
            }
            else -> {}
        }
    }
}