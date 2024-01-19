package com.elfak.smartfarming.ui.components.inputs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.domain.enums.GraphPeriods
import com.elfak.smartfarming.domain.enums.forDisplay
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIcon
import com.elfak.smartfarming.ui.screens.graphScreen.formatBasedOnGraphPeriods
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.SmartFarmingTheme
import java.time.LocalDateTime

@Composable
fun GraphReadingInputField(period: GraphPeriods, startDate: LocalDateTime, endDate: LocalDateTime, isPeriodChosen: Boolean = false, onClick: () -> Unit = { }) {
    Row(
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 10.dp)
                ,
            targetState = isPeriodChosen, label = "") {
            when (it) {
                true -> {
                    Row( modifier = Modifier.padding(horizontal = 5.dp).fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val start = startDate.formatBasedOnGraphPeriods(period)
                            val end = endDate.formatBasedOnGraphPeriods(period)
                            Text(text = "$start - $end" ,style = MaterialTheme.typography.bodyLarge)
                        }
                        Row(
                            Modifier
                                .padding(end = 5.dp)
                                .wrapContentWidth()
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Divider(Modifier.fillMaxHeight(0.85f).width(2.dp), color = FontColor)
                            Spacer(Modifier.width(10.dp))
                            Text(text = period.forDisplay() ,style = MaterialTheme.typography.bodyLarge)
                        }

                    }
                }
                false -> {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Choose period", style = MaterialTheme.typography.bodyLarge)
                        ButtonWithIcon(
                            icon = Icons.Rounded.CalendarMonth,
                            iconColor = FontColor,
                            backgroundColor = Color.Transparent,
                            size = 35.dp
                        ) { }
//                        Column(
//                            modifier = Modifier
//                                .clip(CircleShape)
//                                .background(Color.Transparent)
//                                .padding(5.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Rounded.CalendarMonth,
//                                contentDescription = null,
//                                modifier = Modifier.size(40.dp),
//                                tint = FontColor
//                            )
//
//                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SmartFarmingTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            val date = LocalDateTime.now()
            GraphReadingInputField(period = GraphPeriods.Months, date, date, isPeriodChosen = true)
        }
    }
}