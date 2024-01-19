@file:OptIn(ExperimentalMaterial3Api::class)

package com.elfak.smartfarming.ui.components.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.elfak.smartfarming.R
import com.elfak.smartfarming.data.models.formatDate
import com.elfak.smartfarming.domain.enums.GraphPeriods
import com.elfak.smartfarming.domain.enums.toGraphPeriod
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIconAndText
import com.elfak.smartfarming.ui.components.inputs.DropdownInputField
import com.elfak.smartfarming.ui.screens.graphScreen.formatBasedOnGraphPeriods
import com.elfak.smartfarming.ui.theme.BorderColor
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.Secondary
import com.elfak.smartfarming.ui.theme.SmartFarmingTheme
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDialog(
    previousPeriod: GraphPeriods,
    previousStartDate: LocalDateTime,
    previousEndDate: LocalDateTime,
    onDismiss: () -> Unit,
    onClick: (startDate: LocalDateTime, endDate: LocalDateTime, graphPeriod: GraphPeriods) -> Unit
) {
    var selectedPeriod by remember { mutableStateOf(previousPeriod)}
    var startDate by remember { mutableStateOf(previousStartDate) }
    var endDate by remember { mutableStateOf(previousEndDate) }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }


    Dialog(onDismissRequest = onDismiss) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .height(500.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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
                    items = listOf(GraphPeriods.Hours.name, GraphPeriods.Months.name, GraphPeriods.Years.name),
                    value = selectedPeriod.name,
                    defaultText = "Choose period type",
                    onSelect = { selectedPeriod = it.toGraphPeriod() }
                )

                Row(
                    Modifier
                        .padding(horizontal = 30.dp, vertical = 15.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(
                            text = "From:",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "To:",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(Modifier.width(5.dp))
                    Column {
                        Text(
                            modifier = Modifier.clickable { showStartDatePicker = true },
                            text = startDate.formatBasedOnGraphPeriods(selectedPeriod),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            modifier = Modifier.clickable { showEndDatePicker = true },
                            text = endDate.formatBasedOnGraphPeriods(selectedPeriod),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            Spacer(Modifier.width(10.dp))
            Row {
                ButtonWithIconAndText(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .width(150.dp)
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    text = stringResource(R.string.save),
                    onClick = {
                              onClick(startDate, endDate, selectedPeriod)
                    },
                    icon = Icons.Rounded.Check
                )
                Spacer(Modifier.width(10.dp))
            }
        }
    }
    AnimatedVisibility(visible = showStartDatePicker) {
        DateDialog(showTimePicker = selectedPeriod == GraphPeriods.Hours, onClick = { startDate = it }) {
            showStartDatePicker = false
        }
    }
    AnimatedVisibility(visible = showEndDatePicker) {
        DateDialog(showTimePicker = selectedPeriod == GraphPeriods.Hours, onClick = { endDate = it }) {
            showEndDatePicker = false
        }
    }
}

@Composable
fun DateDialog(
    showTimePicker: Boolean = true,
    onClick: (date: LocalDateTime) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        convertLocalDateTimeToMillis(LocalDateTime.now())
    )
    val timePickerState = rememberTimePickerState(
        initialHour = LocalDateTime.now().hour,
        initialMinute = LocalDateTime.now().minute,
        is24Hour = true
    )
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 10.dp)
        ) {
            DatePicker(
                colors = DatePickerDefaults.colors(
                    titleContentColor = FontColor,
                    headlineContentColor = FontColor,
                    weekdayContentColor = FontColor,
                    subheadContentColor = FontColor,
                    yearContentColor = FontColor,
                    currentYearContentColor = Secondary,
                    selectedYearContentColor = Color.White,
                    selectedYearContainerColor = Secondary,
                    dayContentColor = FontColor,
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Secondary,
                    todayContentColor = FontColor,
                    todayDateBorderColor = Secondary,
                ),
                state = datePickerState)
            if (showTimePicker) {
                TimePicker(
                    colors = TimePickerDefaults.colors(
                        clockDialColor = Secondary.copy(alpha = 0.2f),
                        clockDialSelectedContentColor = Color.White,
                        clockDialUnselectedContentColor = FontColor,
                        selectorColor = Secondary,
                        timeSelectorSelectedContainerColor = Secondary,
                        timeSelectorUnselectedContainerColor = Secondary.copy(alpha = 0.2f),
                        timeSelectorSelectedContentColor = Color.White,
                        timeSelectorUnselectedContentColor = FontColor
                    ),
                    state = timePickerState
                )
            }
            ButtonWithIconAndText(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .width(150.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.secondary),
                text = stringResource(R.string.save),
                onClick = {
                    var date = convertMillisToLocalDateTime(datePickerState.selectedDateMillis)
                    if (date != null) {
                        if (showTimePicker) {
                            date = date.withHour(timePickerState.hour).withMinute(timePickerState.minute)
                        }
                        onClick(date!!)
                    }
                    onDismiss()
                },
                icon = Icons.Rounded.Check
            )
        }
    }
}

fun convertMillisToLocalDateTime(millis: Long?): LocalDateTime? {
    return millis?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}
fun convertLocalDateTimeToMillis(localDateTime: LocalDateTime?): Long? {
    return localDateTime?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
}

@Preview
@Composable
fun DialogPreview() {
    SmartFarmingTheme {
        DateDialog(onDismiss = { }, onClick = {_ -> })
    }
}