package com.elfak.smartfarming.ui.screens.settingsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.elfak.smartfarming.R
import com.elfak.smartfarming.ui.components.ComposableLifecycle
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.theme.Placeholder


@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
) {
    ToastHandler(
        toastData = viewModel.uiState.toastData,
        clearErrorMessage = viewModel::clearErrorMessage,
        clearSuccessMessage = viewModel::clearSuccessMessage
    )
    ComposableLifecycle { _, event ->
        when(event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.prepareData()
            }
            else -> {}
        }
    }

    Column(Modifier.verticalScroll(rememberScrollState())) {
        // general service
        SettingRowContainer {
            SettingItem(
                text = stringResource(R.string.use_background_service_title),
                description = stringResource(R.string.use_service_description),
                switchState = viewModel.uiState.isServiceEnabled,
                onSwitchToggle = {
                    viewModel.toggleService(it)
                }
            )
        }
        Divider()
        // notifications
        SettingRowContainer {
            SettingItem(
                enabled = viewModel.uiState.isServiceEnabled,
                text = stringResource(R.string.sound_notification),
                description = stringResource(id = R.string.enable_sound_notification),
                switchState = viewModel.uiState.isNotificationEnabled,
                onSwitchToggle = {
                    viewModel.toggleNotificationSound(it)
                }
            )
        }
        Divider()
        // real time updates
        SettingRowContainer {
            SettingItem(
                enabled = viewModel.uiState.isServiceEnabled,
                text = stringResource(R.string.real_time_sensor_updates_title),
                description = stringResource(R.string.real_time_sensor_updates_description),
                switchState = viewModel.uiState.isRealTimeUpdatesEnabled,
                onSwitchToggle = {
                    viewModel.toggleRealTimeUpdate(it)
                }
            )
        }
        Divider()
    }
}

@Composable
fun SettingRowContainer(content: @Composable RowScope.() -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 5.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
fun SettingItem(
    enabled: Boolean = true,
    text: String,
    description: String,
    switchState: Boolean,
    onSwitchToggle: (Boolean) -> Unit,
) {
    val textColor = if (enabled) {
        MaterialTheme.colorScheme.onBackground
    }
    else {
        Placeholder // better visibility
    }
    Column(Modifier.fillMaxWidth(0.8f)) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = textColor
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor

        )
    }

    Switch(
        enabled = enabled,
        checked = switchState,
        onCheckedChange = onSwitchToggle,
        colors = SwitchDefaults.colors(
            checkedTrackColor = MaterialTheme.colorScheme.onBackground,
            uncheckedThumbColor =  MaterialTheme.colorScheme.secondary,
            uncheckedBorderColor =  MaterialTheme.colorScheme.secondary,
            uncheckedTrackColor = Color.White,
            disabledCheckedTrackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            disabledUncheckedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            disabledUncheckedThumbColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
        ),
    )
}
