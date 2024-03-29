
package com.elfak.smartfarming.ui.screens.graphScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.elfak.smartfarming.R
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.ui.components.ComposableLifecycle
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIconAndText
import com.elfak.smartfarming.ui.components.cards.DeviceCard
import com.elfak.smartfarming.ui.components.cards.RuleCard
import com.elfak.smartfarming.ui.components.containers.CardContainerWithTitle
import com.elfak.smartfarming.ui.components.dialogs.CalendarDialog
import com.elfak.smartfarming.ui.components.graphs.LineGraphChart
import com.elfak.smartfarming.ui.components.inputs.GraphReadingInputField

@Composable
fun GraphScreen(
    viewModel: GraphScreenViewModel,
    navigateBack: () -> Unit = { },
    navigateToDeviceDetails: (deviceId: String, screenState: ScreenState) -> Unit,
    navigateToRuleDetails: (ruleId: String?, screenState: ScreenState) -> Unit,
) {
    val sensor by viewModel.sensorLiveData.observeAsState(initial = Device())
    val actuator by viewModel.actuatorLiveData.observeAsState(initial = Device())


    var showCalendarDialog by remember { mutableStateOf(false) }

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
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LineGraphChart(readings = viewModel.uiState.readings, period = viewModel.uiState.graphPeriod)
            Spacer(modifier = Modifier.height(10.dp))
            // calendar
            Column(
                Modifier
                    .wrapContentWidth()
                    .height(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GraphReadingInputField(
                    period = viewModel.uiState.graphPeriod,
                    startDate = viewModel.uiState.startDate,
                    endDate = viewModel.uiState.endDate,
                    isPeriodChosen = viewModel.uiState.isPeriodChosen
                ) {
                    showCalendarDialog = true
                }
                if (showCalendarDialog) {
                    CalendarDialog(
                        previousPeriod = viewModel.uiState.graphPeriod,
                        previousStartDate = viewModel.uiState.startDate,
                        previousEndDate = viewModel.uiState.endDate,
                        onDismiss = {
                            viewModel.setDates(isChosen = false)
                            showCalendarDialog = false
                        },
                        onClick = { startDate, endDate, period ->
                            viewModel.setDates(startDate, endDate, true)
                            viewModel.setGraphPeriod(period)
                            showCalendarDialog = false
                            viewModel.refreshGraph()
                        },
                    )
                }

            }
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
            DeviceCard(
                device = sensor,
                menuItems = prepareMenuItems(
                    sensor.id,
                    onEdit = navigateToDeviceDetails,
                    onDelete = {
                        viewModel.deleteDevice(sensor.id, DeviceTypes.Sensor) {
                            navigateBack()
                        }
                    }
                ),
                onCardClick = { navigateToDeviceDetails(sensor.id, ScreenState.View) },
                onBellIconClick = { viewModel.onDeviceBellIconClicked(sensor.id, DeviceTypes.Sensor) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (actuator != null) {
                DeviceCard(
                    device = actuator!!,
                    menuItems = prepareMenuItems(
                        actuator!!.id,
                        onEdit = navigateToDeviceDetails,
                        onDelete = {
                            viewModel.deleteDevice(actuator!!.id, DeviceTypes.Actuator)
                        }
                    ),
                    onCardClick = { navigateToDeviceDetails(actuator!!.id, ScreenState.View) },
                    onBellIconClick = { viewModel.onDeviceBellIconClicked(actuator!!.id, DeviceTypes.Actuator) }

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
