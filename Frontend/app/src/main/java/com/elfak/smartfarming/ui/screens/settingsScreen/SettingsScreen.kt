package com.elfak.smartfarming.ui.screens.settingsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.R


@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel
) {
    Column {
        SettingRowContainer {

            SettingItem(
                text = stringResource(R.string.sound_notification),
                description = stringResource(id = R.string.enable_sound_notification),
                switchState = viewModel.uiState.isNotificationEnabled,
                onSwitchToggle = {
                    viewModel.toggleNotificationSound(it)
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
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content()
    }
}

@Composable
fun SettingItem(
    text: String,
    description: String,
    switchState: Boolean,
    onSwitchToggle: (Boolean) -> Unit,
) {
    Column(Modifier.fillMaxWidth(0.8f)) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground

        )
    }
    Switch(
        checked = switchState,
        onCheckedChange = onSwitchToggle,
        colors = SwitchDefaults.colors(
            checkedTrackColor = MaterialTheme.colorScheme.onBackground,
            uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
            uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
            uncheckedTrackColor = Color.White
        ),
    )
}

//@Preview
//@Composable
//fun SwithchPreview() {
//    SmartFarmingTheme {
//        Column {
//            Switch(
//                checked = true,
//                onCheckedChange = null,
//                colors = SwitchDefaults.colors(
//                    checkedTrackColor = MaterialTheme.colorScheme.onBackground,
//                ),
//            )
//            Spacer(Modifier.height(5.dp))
//            Switch(
//                checked = false,
//                onCheckedChange = null,
//                colors = SwitchDefaults.colors(
//                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
//                    uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
//                    uncheckedTrackColor = Color.White
//                ),
//            )
//        }
//    }
//}