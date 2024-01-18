
package com.elfak.smartfarming.ui.screens.graphScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cable
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.elfak.smartfarming.R
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.ui.components.ComposableLifecycle
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIconAndText
import com.elfak.smartfarming.ui.components.cards.DeviceCard
import com.elfak.smartfarming.ui.components.cards.RuleCard
import com.elfak.smartfarming.ui.components.containers.CardContainerWithTitle
import com.elfak.smartfarming.ui.components.graphs.GraphChart

@Composable
fun GraphScreen(
    viewModel: GraphScreenViewModel,
    navigateBack: () -> Unit = { },
    navigateToDeviceDetails: (deviceId: String, screenState: ScreenState) -> Unit,
    navigateToRuleDetails: (ruleId: String?, screenState: ScreenState) -> Unit,
) {
    rememberCoroutineScope()
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
    ) {
        // graph
        Column(
            Modifier
                .fillMaxWidth()
                .height(250.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GraphChart(
                readings = viewModel.uiState.readings,
                graphPeriods = viewModel.uiState.graphPeriod, // todo needs to be dynamic
            )
            // calendar
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(2.dp, Color.Red)
            ) { }
        }
        Spacer(modifier = Modifier.height(15.dp))
        CardContainerWithTitle(title = stringResource(R.string.related_rule)) {
            if (viewModel.uiState.rule != null) {
                // rule card
                val ruleId = viewModel.uiState.rule!!.id
                RuleCard(
                    viewModel.uiState.rule!!,
                    menuItems = prepareMenuItems(
                        ruleId,
                        onEdit = navigateToRuleDetails,
                        onDelete = { viewModel.deleteRule(ruleId) }
                    ),
                    onCardClick = { navigateToRuleDetails(ruleId, ScreenState.View) }
                )
            }
            else {
                // Add Rule button
                Column(
                    Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
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
        Spacer(modifier = Modifier.height(10.dp))
        // devices cards
        CardContainerWithTitle(title = stringResource(R.string.related_devices)) {
            val sensorId = viewModel.uiState.sensor.id
            DeviceCard(
                device = viewModel.uiState.sensor,
                menuItems = prepareMenuItems(
                    sensorId,
                    onEdit = navigateToDeviceDetails,
                    onDelete = {
                        viewModel.deleteDevice(sensorId, DeviceTypes.Sensor) {
                            navigateBack()
                        }
                    }
                ),
                onCardClick = { navigateToDeviceDetails(sensorId, ScreenState.View) },
                onBellIconClick = { viewModel.onDeviceBellIconClicked(sensorId, DeviceTypes.Sensor) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (viewModel.uiState.actuator != null) {
                val actuatorId = viewModel.uiState.actuator!!.id

                DeviceCard(
                    device = viewModel.uiState.actuator!!,
                    menuItems = prepareMenuItems(
                        actuatorId,
                        onEdit = navigateToDeviceDetails,
                        onDelete = {
                            viewModel.deleteDevice(actuatorId, DeviceTypes.Actuator)
                        }
                    ),
                    onCardClick = { navigateToDeviceDetails(actuatorId, ScreenState.View) },
                    onBellIconClick = { viewModel.onDeviceBellIconClicked(actuatorId, DeviceTypes.Actuator) }

                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

private fun prepareMenuItems(
    id: String,
    onEdit: (id: String, screenState: ScreenState) -> Unit,
    onDelete: (id: String) -> Unit
): List<MenuItem> {
    return listOf(
        MenuItem("Edit", Icons.Rounded.Edit, action = { onEdit(id, ScreenState.Edit) }),
        MenuItem("Delete", Icons.Rounded.Delete, action = { onDelete(id) })
    )
}
