@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.components.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.ui.theme.BackgroundVariant
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.Secondary
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

@Composable
fun GraphCardContainer(
    sensors: List<Device>,
    readings: Map<String, List<GraphReading>> = emptyMap(),
    refreshState: PullRefreshState,
    isRefreshing: Boolean,
    onCardClick: (deviceId: String) -> Unit = { },
) {
    Box(modifier = Modifier.pullRefresh(refreshState)) {
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(sensors) { sensor ->
                readings[sensor.id]?.let {
                    GraphCard(
                        sensor = sensor,
                        readings = it,
                        onCardClick = onCardClick,
                    )
                }
            }
        }
        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))

    }
}

@Composable
fun GraphCard(sensor: Device, readings: List<GraphReading> = emptyList(), onCardClick: (deviceId: String) -> Unit) {
    Column(
        Modifier
            .padding(10.dp)
            .height(200.dp)
            .fillMaxWidth(0.9f)
            .clickable { onCardClick(sensor.id) },
        ) {
        Text(
            text = sensor.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Column(Modifier.fillMaxSize()) {

            ProvideChartStyle(
                chartStyle = ChartStyle.fromColors(
                    axisLabelColor = FontColor,
                    axisGuidelineColor = FontColor,
                    axisLineColor = FontColor,
                    entityColors = listOf(Secondary),
                    elevationOverlayColor = BackgroundVariant
                ),
            ) {
                val data = readings
                    .map { it.timeMeasured to it.reading.toFloat() } // x and y values for graph
                    .associate { (date, yValue) -> date to yValue }

                // converting data to float value
                val xValuesToDates = data.keys.associateBy { it.toEpochSecond(ZoneOffset.UTC).toFloat() }
                // creating entries - x and y's for graph
                val chartEntryModel = entryModelOf(xValuesToDates.keys.zip(data.values, ::entryOf))

                val count = chartEntryModel.entries[0].count() // will define if is column chart or line
                val chartType = if (count == 1) columnChart() else lineChart()
                Chart(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(5.dp),
                    model = chartEntryModel,
                    chart = chartType,
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(
                        valueFormatter = AxisHourFormatter()
                    ),
                )
            }
        }
    }
}

class AxisHourFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    override fun formatValue(value: Float, chartValues: ChartValues): CharSequence {
        // convert int value of time to time
        val time = LocalDateTime.ofEpochSecond(value.toLong(), 0, ZoneOffset.UTC).toLocalTime()
        return time.truncatedTo(ChronoUnit.MINUTES).toString()
    }
}
