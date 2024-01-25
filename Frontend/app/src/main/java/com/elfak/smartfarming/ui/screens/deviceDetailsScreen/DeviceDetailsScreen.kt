package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material.icons.rounded.Cable
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.elfak.smartfarming.R
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.formatShortDate
import com.elfak.smartfarming.domain.enums.DeviceState
import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.enums.toDeviceState
import com.elfak.smartfarming.domain.enums.toDeviceStatus
import com.elfak.smartfarming.domain.enums.toDeviceType
import com.elfak.smartfarming.ui.components.ComposableLifecycle
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIcon
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIconAndText
import com.elfak.smartfarming.ui.components.buttons.DeleteIconButton
import com.elfak.smartfarming.ui.components.cards.RuleCard
import com.elfak.smartfarming.ui.components.containers.CardContainerWithTitle
import com.elfak.smartfarming.ui.components.inputs.BasicInputField
import com.elfak.smartfarming.ui.components.inputs.DropdownInputField
import com.elfak.smartfarming.ui.theme.BorderColor
import com.elfak.smartfarming.ui.theme.Disabled
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsScreenViewModel,
    navigateBack: () -> Unit = { },
    navigateToRuleDetails: (ruleId: String?, screenState: ScreenState) -> Unit,
) {
    val device by viewModel.deviceLiveData.observeAsState(initial = Device())
    ComposableLifecycle { _, event ->
        when(event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.loadData()
            }
            else -> {}
        }
    }
    ToastHandler(
        toastData = viewModel.uiState.toastData,
        clearErrorMessage = viewModel::clearErrorMessage,
        clearSuccessMessage = viewModel::clearSuccessMessage
    )

    Column(
        Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState(0)),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // device details
        Column(Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            when (viewModel.uiState.screenState) {
                ScreenState.Edit -> DeviceDetailsEditTab(
                    device = device?: Device(),
                    deviceActions = viewModel.uiState.deviceActions,
                    onBellIconClick = { viewModel.onDeviceBellIconClicked() },
                    onEditIconClick = { viewModel.setScreenState(ScreenState.View)},
                    onButtonClick = { viewModel.saveDevice() })
                ScreenState.View -> DeviceDetailsViewTab(
                    device = device?: Device(),
                    onBellIconClick = { viewModel.onDeviceBellIconClicked() },
                    onEditIconClick = { viewModel.setScreenState(ScreenState.Edit)},
                    onButtonClick = {
                        viewModel.deleteDevice {
                            navigateBack()
                        }
                    })
                ScreenState.Create -> DeviceDetailsCreateTab(
                    device = device?: Device(),
                    deviceActions = viewModel.uiState.deviceActions,
                    onBellIconClick = { viewModel.onDeviceBellIconClicked() },
                    onButtonClick = { viewModel.saveDevice() })
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        // related rule
        if (viewModel.uiState.screenState != ScreenState.Create) {
            CardContainerWithTitle(title = stringResource(R.string.related_rule)) {
                if (viewModel.uiState.rule != null) {
                    // rule card
                    val ruleId = viewModel.uiState.rule!!.id
                    RuleCard(
                        viewModel.uiState.rule!!,
                        showMenu = false,
                        onCardClick = { navigateToRuleDetails(ruleId, ScreenState.View) }
                    )
                    // Delete rule button
                    Column(
                        Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        ButtonWithIconAndText(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .width(180.dp)
                                .height(60.dp)
                                .background(MaterialTheme.colorScheme.error),
                            text = stringResource(R.string.delete_rule),
                            onClick = { viewModel.deleteRule(ruleId) },
                            icon = Icons.Rounded.Delete
                        )
                    }
                }
                else {
                    // Add Rule button
                    Column(
                        Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = stringResource(R.string.no_existing_rule_for_this_sensor),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        ButtonWithIconAndText(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .width(180.dp)
                                .height(60.dp)
                                .background(MaterialTheme.colorScheme.secondary),
                            text = stringResource(R.string.add_rule),
                            onClick = { navigateToRuleDetails(null, ScreenState.Create) },
                            icon = Icons.Rounded.Cable
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun DeviceDetailsCreateTab(
    device: Device,
    deviceActions: Map<String, (Any?) -> Unit> = emptyMap(),
    onBellIconClick: (id: String) -> Unit = { },
    onButtonClick: () -> Unit = { },
) {
    // buttons row
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        val notifyIcon = when (device.isMuted) {
            true -> Icons.Outlined.NotificationsOff
            false -> Icons.Outlined.Notifications
        }
        // notification bell
        ButtonWithIcon(
            icon = notifyIcon,
            backgroundColor = Color.Transparent,
            iconColor = FontColor,
            size = 35.dp
        ) {
            onBellIconClick(device.id)
        }
    }
    Column(
        Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // name
        BasicInputField(
            text = device.name,
            onTextChanged = { deviceActions["setName"]?.invoke(it) },
            label = stringResource(id = R.string.device_name) + ":",
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            placeholder = "E.g.Humidity sensor"
        )
        // type
        DropdownInputField(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(290.dp)
                .height(56.dp)
                .padding(horizontal = 5.dp)
                .border(
                    color = BorderColor,
                    width = 1.dp,
                    shape = RoundedCornerShape(30.dp)
                ),
            value = device.type.name,
            defaultText = "Choose device type:",
            items = listOf(DeviceTypes.Sensor.name, DeviceTypes.Actuator.name),
            onSelect = { deviceActions["setType"]?.invoke(it.toDeviceType()) }
        )
        // status
        DropdownInputField(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(290.dp)
                .height(56.dp)
                .padding(horizontal = 5.dp)
                .border(
                    color = BorderColor,
                    width = 1.dp,
                    shape = RoundedCornerShape(30.dp)
                ),
            value = device.status.name,
            defaultText = "Choose device status:",
            items = listOf(DeviceStatus.Online.name, DeviceStatus.Offline.name),
            onSelect = { deviceActions["setStatus"]?.invoke(it.toDeviceStatus()) }
        )

        AnimatedContent(device.type, label = "") { type ->
            when (type) {
                DeviceTypes.Sensor -> {
                    Column {
                        // unit
                        BasicInputField(
                            text = device.unit.orEmpty(),
                            onTextChanged = { deviceActions["setUnit"]?.invoke(it.trim()) },
                            label = stringResource(id = R.string.device_unit) + ":",
                            keyboardOptions = KeyboardOptions.Default.copy(
                                autoCorrect = false,
                                capitalization = KeyboardCapitalization.None,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            placeholder = "E.g. % or Celsius"
                        )
                        // last reading - disabled
                        BasicInputField(
                            text = "${device.lastReading}",
                            label = stringResource(id = R.string.device_last_reading) + ":",
                            enabled = false
                        )
                    }
                }
                DeviceTypes.Actuator -> {
                    // state
                    DropdownInputField(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(290.dp)
                            .height(56.dp)
                            .padding(horizontal = 5.dp)
                            .border(
                                color = BorderColor,
                                width = 1.dp,
                                shape = RoundedCornerShape(30.dp)
                            ),
                        value = device.state!!.name,
                        defaultText = "Choose device state:",
                        items = listOf(DeviceState.On.name, DeviceState.Off.name),
                        onSelect = { deviceActions["setState"]?.invoke(it.toDeviceState()) }
                    )
                }
            }
        }
    }
    Spacer(Modifier.height(30.dp))
    ButtonWithIconAndText(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .width(180.dp)
            .height(60.dp)
            .background(MaterialTheme.colorScheme.secondary),
        text = stringResource(R.string.save_changes),
        onClick = onButtonClick,
        icon = Icons.Rounded.Check
    )
}

@Composable
fun DeviceDetailsViewTab(
    device: Device,
    onBellIconClick: (id: String) -> Unit = { },
    onEditIconClick: () -> Unit = { },
    onButtonClick: () -> Unit = { },

) {
    // buttons row
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        val notifyIcon = when (device.isMuted) {
            true -> Icons.Outlined.NotificationsOff
            false -> Icons.Outlined.Notifications
        }
        // notification bell
        ButtonWithIcon(
            icon = notifyIcon,
            backgroundColor = Color.Transparent,
            iconColor = FontColor,
            size = 35.dp
        ) {
            onBellIconClick(device.id)
        }
        // edit
        ButtonWithIcon(
            icon = Icons.Rounded.Edit,
            iconColor = FontColor,
            backgroundColor = Color.Transparent
        ) {
            onEditIconClick()
        }
    }
    Column(
        Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // name
        BasicInputField(
            text = device.name,
            label = stringResource(id = R.string.device_name) + ":",
            enabled = false
        )
        // type
        DropdownInputField(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(290.dp)
                .height(56.dp)
                .padding(horizontal = 5.dp)
                .border(
                    color = Disabled,
                    width = 1.dp,
                    shape = RoundedCornerShape(30.dp)
                ),
            value = device.type.name,
            enabled = false
        )
        // status
        DropdownInputField(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(290.dp)
                .height(56.dp)
                .padding(horizontal = 5.dp)
                .border(
                    color = Disabled,
                    width = 1.dp,
                    shape = RoundedCornerShape(30.dp)
                ),
            value = device.status.name,
            enabled = false
        )
        when (device.type) {
            DeviceTypes.Sensor -> {
                Column {
                    // unit
                    BasicInputField(
                        text = device.unit.orEmpty(),
                        label = stringResource(id = R.string.device_unit) + ":",
                        enabled = false
                    )
                    // last reading - disabled
                    BasicInputField(
                        text = "${device.lastReading} at [${device.lastReadingTime.formatShortDate()}]",
                        label = stringResource(id = R.string.device_last_reading) + ":",
                        enabled = false
                    )
                }
            }
            DeviceTypes.Actuator -> {
                // state
                DropdownInputField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(290.dp)
                        .height(56.dp)
                        .padding(horizontal = 5.dp)
                        .border(
                            color = Disabled,
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    value = device.state!!.name,
                    enabled = false
                )
            }
        }
    }
    Spacer(Modifier.height(30.dp))
    ButtonWithIconAndText(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .width(180.dp)
            .height(60.dp)
            .background(MaterialTheme.colorScheme.error),
        text = stringResource(R.string.delete_device),
        onClick = onButtonClick,
        icon = Icons.Rounded.Delete
    )
}

@Composable
fun DeviceDetailsEditTab(
    device: Device,
    deviceActions: Map<String, (Any?) -> Unit> = emptyMap(),
    onBellIconClick: (id: String) -> Unit = { },
    onEditIconClick: () -> Unit = { },
    onButtonClick: () -> Unit = { },

) {
    // buttons row
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        val notifyIcon = when (device.isMuted) {
            true -> Icons.Outlined.NotificationsOff
            false -> Icons.Outlined.Notifications
        }
        // notification bell
        ButtonWithIcon(
            icon = notifyIcon,
            backgroundColor = Color.Transparent,
            iconColor = FontColor,
            size = 35.dp
        ) {
            onBellIconClick(device.id)
        }
        // edit - close
        DeleteIconButton { onEditIconClick() }
    }
    Column(
        Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // name
        BasicInputField(
            text = device.name,
            label = stringResource(id = R.string.device_name) + ":",
            enabled = false
        )
        // type
        DropdownInputField(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(290.dp)
                .height(56.dp)
                .padding(horizontal = 5.dp)
                .border(
                    color = Disabled,
                    width = 1.dp,
                    shape = RoundedCornerShape(30.dp)
                ),
            value = device.type.name,
            enabled = false
        )
        // status
        DropdownInputField(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(290.dp)
                .height(56.dp)
                .padding(horizontal = 5.dp)
                .border(
                    color = Disabled,
                    width = 1.dp,
                    shape = RoundedCornerShape(30.dp)
                ),
            value = device.status.name,
            enabled = false
        )
        when (device.type) {
            DeviceTypes.Sensor -> {
                Column {
                    // unit
                    BasicInputField(
                        text = device.unit.orEmpty(),
                        onTextChanged = { deviceActions["setUnit"]?.invoke(it.trim()) },
                        label = stringResource(id = R.string.device_unit) + ":",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            autoCorrect = false,
                            capitalization = KeyboardCapitalization.None,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        placeholder = "E.g. % or Celsius"
                    )
                    // last reading - disabled
                    BasicInputField(
                        text = "${device.lastReading} at [${device.lastReadingTime.formatShortDate()}]",
                        label = stringResource(id = R.string.device_last_reading) + ":",
                        enabled = false
                    )
                }
            }
            DeviceTypes.Actuator -> {
                // state
                DropdownInputField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(290.dp)
                        .height(56.dp)
                        .padding(horizontal = 5.dp)
                        .border(
                            color = Disabled,
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    value = device.state!!.name,
                    enabled = false
                )
            }
        }
    }
    Spacer(Modifier.height(30.dp))
    ButtonWithIconAndText(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .width(180.dp)
            .height(60.dp)
            .background(MaterialTheme.colorScheme.secondary),
        text = stringResource(R.string.save_changes),
        onClick = onButtonClick,
        icon = Icons.Rounded.Check
    )
}