package com.elfak.smartfarming.ui.components.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.domain.enums.GraphPeriods
import com.elfak.smartfarming.domain.utils.graphFormatters.AxisHourFormatter
import com.elfak.smartfarming.domain.utils.graphFormatters.AxisMonthFormatter
import com.elfak.smartfarming.domain.utils.graphFormatters.AxisYearFormatter
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
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

@Composable
fun GraphChart(readings: List<GraphReading> = emptyList(), graphPeriods: GraphPeriods = GraphPeriods.Hours,) {
    if (readings.isEmpty()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Data will be available after a while." )
        }
        return
    }
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
        val xValuesToDates = data.keys.associateBy { it.truncatedTo(ChronoUnit.HOURS).toEpochSecond(ZoneOffset.UTC).toFloat() }
        // creating entries - x and y's for graph
        val chartEntryModel = entryModelOf(xValuesToDates.keys.zip(data.values, ::entryOf))

        val count = chartEntryModel.entries[0].count() // will define if is column chart or line
        val valueFormatter = when (graphPeriods) {
            GraphPeriods.Hours -> AxisHourFormatter()
            GraphPeriods.Months -> AxisMonthFormatter()
            GraphPeriods.Years -> AxisYearFormatter()
        }
        val chartType = if (count == 1) columnChart() else lineChart()
        Chart(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(5.dp),
            model = chartEntryModel,
            chart = columnChart(),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(
                valueFormatter = valueFormatter,
            ),
        )
    }
}