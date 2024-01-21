package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

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
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.RuleExpressionType
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.domain.enums.toRuleExpressionType
import com.elfak.smartfarming.domain.enums.toSignString
import com.elfak.smartfarming.ui.components.ComposableLifecycle
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIcon
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIconAndText
import com.elfak.smartfarming.ui.components.buttons.DeleteIconButton
import com.elfak.smartfarming.ui.components.cards.DeviceCard
import com.elfak.smartfarming.ui.components.containers.CardContainerWithTitle
import com.elfak.smartfarming.ui.components.inputs.BasicInputField
import com.elfak.smartfarming.ui.components.inputs.DropdownInputField
import com.elfak.smartfarming.ui.theme.BorderColor
import com.elfak.smartfarming.ui.theme.Disabled
import com.elfak.smartfarming.ui.theme.FontColor


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
                ScreenState.Edit -> RuleDetailsEditTab(
                    rule = viewModel.uiState.rule,
                    ruleActions = viewModel.uiState.ruleActions,
                    onEditIconClick = { viewModel.setScreenState(ScreenState.View)},
                    onButtonClick = { viewModel.saveRule() })
                ScreenState.View -> RuleDetailsViewTab(
                    rule = viewModel.uiState.rule,
                    onEditIconClick = { viewModel.setScreenState(ScreenState.Edit)},
                    onButtonClick = {
                        viewModel.deleteRule {
                            navigateBack()
                        }
                    })
                ScreenState.Create -> RuleDetailsCreateTab(
                    rule = viewModel.uiState.rule,
                    ruleActions = viewModel.uiState.ruleActions,
                    onButtonClick = { viewModel.saveRule() })
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        // related devices
        CardContainerWithTitle(title = stringResource(R.string.related_devices)) {
            val showNoDevicesMessage = viewModel.uiState.screenState == ScreenState.Create
                    && viewModel.uiState.selectActuators.isEmpty()
                    && viewModel.uiState.selectSensors.isEmpty()
            if (showNoDevicesMessage) {
                Text(
                    text = stringResource(R.string.no_available_devices),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            else {
                val sensorId = viewModel.uiState.sensor.id
                AnimatedContent (sensorId.isBlank(), label = "") { isBlank ->
                    when (isBlank) {
                        true -> {
                            val dropdownItems = viewModel.uiState.selectSensors.map { it.name }
                            DropdownInputField(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .width(290.dp)
                                    .height(56.dp)
                                    .padding(end = 10.dp)
                                    .border(
                                        color = BorderColor,
                                        width = 1.dp,
                                        shape = RoundedCornerShape(30.dp)
                                    ),
                                value = if (dropdownItems.isNotEmpty()) sensorId else "No sensors available",
                                defaultText = "Choose sensor:",
                                items = dropdownItems.ifEmpty { listOf("") },
                                onSelect = { viewModel.setDeviceFromSelect(it, DeviceTypes.Sensor) }
                            )
                        }
                        false -> {
                            DeviceCard(
                                device = viewModel.uiState.sensor,
                                showMenu = false,
                                onCardClick = {
                                    if (viewModel.uiState.screenState == ScreenState.Create) {
                                        viewModel.setDeviceFromSelect("", DeviceTypes.Sensor)
                                    } else {
                                        navigateToDeviceDetails(sensorId, ScreenState.View)
                                    }
                                },
                                onBellIconClick = { viewModel.onDeviceBellIconClicked(sensorId, DeviceTypes.Sensor) }
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                val actuatorId = viewModel.uiState.actuator.id
                AnimatedContent (actuatorId.isBlank(), label = "") { isBlank ->
                    when(isBlank) {
                        true -> {
                            val dropdownItems = viewModel.uiState.selectActuators.map { it.name }
                            DropdownInputField(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .width(290.dp)
                                    .height(56.dp)
                                    .padding(end = 10.dp)
                                    .border(
                                        color = BorderColor,
                                        width = 1.dp,
                                        shape = RoundedCornerShape(30.dp)
                                    ),
                                value = if (dropdownItems.isNotEmpty()) actuatorId else "No actuators available",
                                defaultText = "Choose actuator:",
                                items = dropdownItems.ifEmpty { listOf("") },
                                onSelect = { viewModel.setDeviceFromSelect(it, DeviceTypes.Actuator) }
                            )
                        }
                        false -> {
                            DeviceCard(
                                device = viewModel.uiState.actuator,
                                showMenu = false,
                                onCardClick = {
                                    if (viewModel.uiState.screenState == ScreenState.Create) {
                                        viewModel.setDeviceFromSelect("", DeviceTypes.Actuator)
                                    } else {
                                        navigateToDeviceDetails(actuatorId, ScreenState.View)
                                    }
                                },
                                onBellIconClick = { viewModel.onDeviceBellIconClicked(actuatorId, DeviceTypes.Actuator) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun RuleDetailsCreateTab(
    rule: Rule,
    ruleActions: Map<String, (Any?) -> Unit>,
    onButtonClick: () -> Unit
) {
    // buttons row
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//        DeleteIconButton { onEditIconClick() }
    }
    Spacer(Modifier.height(5.dp))
    Column(
        Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .wrapContentSize()
    ) {
        // name
        BasicInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            text = rule.name,
            onTextChanged = { ruleActions["setName"]?.invoke(it) },
            label = stringResource(id = R.string.rule_name) + ":",
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            placeholder = "E.g.Rule for lawn"
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.wrapContentSize()) {
            // expressions
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                // start
                DropdownInputField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(140.dp)
                        .height(56.dp)
                        .padding(end = 10.dp)
                        .border(
                            color = BorderColor,
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    value = rule.startExpression.toSignString(),
                    defaultText = "Choose start expression",
                    items = listOf(RuleExpressionType.Smaller.toSignString(), RuleExpressionType.Larger.toSignString()),
                    onSelect = { ruleActions["setStartExpression"]?.invoke(it.toRuleExpressionType()) }
                )
                // stop
                DropdownInputField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(140.dp)
                        .height(56.dp)
                        .padding(end = 10.dp)
                        .border(
                            color = BorderColor,
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    value = rule.stopExpression.toSignString(),
                    defaultText = "Choose stop expression",
                    items = listOf(RuleExpressionType.Smaller.toSignString(), RuleExpressionType.Larger.toSignString()),
                    onSelect = { ruleActions["setStopExpression"]?.invoke(it.toRuleExpressionType()) }
                )
            }
            // levels
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                // start
                BasicInputField(
                    modifier = Modifier.width(140.dp),
                    text = rule.startTriggerLevel,
                    onTextChanged = { ruleActions["setStartTriggerLevel"]?.invoke(it) },
                    label = stringResource(id = R.string.start_trigger_level) + ":",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = "E.g. 30"
                )
                // stop
                BasicInputField(
                    modifier = Modifier.width(140.dp),
                    text = rule.stopTriggerLevel,
                    onTextChanged = { ruleActions["setStopTriggerLevel"]?.invoke(it) },
                    label = stringResource(id = R.string.stop_trigger_level) + ":",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = "E.g. 80"
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

@Composable
fun RuleDetailsViewTab(
    rule: Rule,
    onEditIconClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    // buttons row
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        // edit
        ButtonWithIcon(
            icon = Icons.Rounded.Edit,
            iconColor = FontColor,
            backgroundColor = Color.Transparent
        ) {
            onEditIconClick()
        }
    }
    Spacer(Modifier.height(5.dp))
    Column(
        Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .wrapContentSize()
    ) {
        // name
        BasicInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            text = rule.name,
            label = stringResource(id = R.string.rule_name) + ":",
            enabled = false
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.wrapContentSize()) {
            // expressions
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                // start
                DropdownInputField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(140.dp)
                        .height(56.dp)
                        .padding(end = 10.dp)
                        .border(
                            color = Disabled,
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    value = rule.startExpression.toSignString(),
                    enabled = false
                )
                // stop
                DropdownInputField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(140.dp)
                        .height(56.dp)
                        .padding(end = 10.dp)
                        .border(
                            color = Disabled,
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    value = rule.stopExpression.toSignString(),
                    enabled = false
                )
            }
            // levels
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                // start
                BasicInputField(
                    modifier = Modifier.width(140.dp),
                    text = rule.startTriggerLevel,
                    label = stringResource(id = R.string.start_trigger_level) + ":",
                    enabled = false
                )
                // stop
                BasicInputField(
                    modifier = Modifier.width(140.dp),
                    text = rule.stopTriggerLevel,
                    label = stringResource(id = R.string.stop_trigger_level) + ":",
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
        text = stringResource(R.string.delete_rule),
        onClick = onButtonClick,
        icon = Icons.Rounded.Delete
    )
}

@Composable
fun RuleDetailsEditTab(
    rule: Rule,
    ruleActions: Map<String, (Any?) -> Unit>,
    onEditIconClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    // buttons row
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        // edit
        DeleteIconButton { onEditIconClick() }
    }
    Spacer(Modifier.height(5.dp))
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            text = rule.name,
            onTextChanged = { ruleActions["setName"]?.invoke(it) },
            label = stringResource(id = R.string.rule_name) + ":",
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            placeholder = "E.g.Rule for lawn"
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.wrapContentSize()) {
            // expressions
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                // start
                DropdownInputField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(140.dp)
                        .height(56.dp)
                        .padding(end = 10.dp)
                        .border(
                            color = BorderColor,
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    value = rule.startExpression.toSignString(),
                    defaultText = "Choose start expression",
                    items = listOf(RuleExpressionType.Smaller.toSignString(), RuleExpressionType.Larger.toSignString()),
                    onSelect = { ruleActions["setStartExpression"]?.invoke(it.toRuleExpressionType()) }
                )
                // stop
                DropdownInputField(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(140.dp)
                        .height(56.dp)
                        .padding(end = 10.dp)
                        .border(
                            color = BorderColor,
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    value = rule.stopExpression.toSignString(),
                    defaultText = "Choose stop expression",
                    items = listOf(RuleExpressionType.Smaller.toSignString(), RuleExpressionType.Larger.toSignString()),
                    onSelect = { ruleActions["setStopExpression"]?.invoke(it.toRuleExpressionType()) }
                )
            }
            // levels
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                // start
                BasicInputField(
                    modifier = Modifier.width(140.dp),
                    text = rule.startTriggerLevel,
                    onTextChanged = { ruleActions["setStartTriggerLevel"]?.invoke(it) },
                    label = stringResource(id = R.string.start_trigger_level) + ":",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = "E.g. 30"
                )
                // stop
                BasicInputField(
                    modifier = Modifier.width(140.dp),
                    text = rule.stopTriggerLevel,
                    onTextChanged = { ruleActions["setStopTriggerLevel"]?.invoke(it) },
                    label = stringResource(id = R.string.stop_trigger_level) + ":",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = "E.g. 80"
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