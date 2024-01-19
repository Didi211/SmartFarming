package com.elfak.smartfarming.ui.components.graphs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.domain.enums.GraphPeriods
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.Secondary
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun LineGraphChart(readings: List<GraphReading> = emptyList(), period: GraphPeriods = GraphPeriods.Hours) {

    if (readings.isEmpty()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Data will be available after a while." )
        }
        return
    }

    val readingsList = if (readings.size < 2) {
        val timeBefore = getTimeBefore(readings.first().timeMeasured, period)
        val timeAfter = getTimeAfter(readings.first().timeMeasured, period)
        val mutableReadings = mutableListOf<GraphReading>()
        mutableReadings.add(GraphReading(timeBefore))
        mutableReadings.add(readings.first())
        mutableReadings.add(GraphReading(timeAfter))
        mutableReadings
    } else {
        readings
    }
    val pointsData = mutableListOf<Point>()
    readingsList.forEachIndexed { index, reading ->
        pointsData.add(Point(index.toFloat(), reading.reading.toFloat()))

    }


    val minValue = pointsData.minBy { it.y }
    val maxValue = pointsData.maxBy { it.y }
    val xAxisData = AxisData.Builder()
        .labelAndAxisLinePadding(10.dp)
        .axisStepSize(if (pointsData.size == 3) 120.dp else 50.dp)
        .backgroundColor(Color.Transparent)
        .axisLineColor(FontColor)
        .axisLabelColor(FontColor)
        .steps(pointsData.size - 1)
        .labelData { i ->
            formatToTime(readingsList[i].timeMeasured, period)
        }
        .shouldDrawAxisLineTillEnd(true)
        .build()

    val steps = 5
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .axisLineColor(FontColor)
        .axisLabelColor(FontColor)
        .labelAndAxisLinePadding(10.dp)
        .labelData { i ->
            val yScale = (maxValue.y - minValue.y) / steps
            (minValue.y + i * yScale).formatToSinglePrecision()
        }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(color = Secondary),
                    IntersectionPoint(color = Secondary),
                    SelectionHighlightPoint(color = FontColor),
                    ShadowUnderLine(color = Secondary, alpha = 0.25f),
                    SelectionHighlightPopUp(
                        backgroundAlpha = 1f,
                        labelColor = FontColor,
                        paddingBetweenPopUpAndPoint = 10.dp,
                        popUpLabel = { x, y ->
                            val time = formatToTime(readingsList[x.toInt()].timeMeasured, period)
                            "${y.formatToSinglePrecision()} on $time" }
                    )
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White,

    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .height(300.dp),
        lineChartData = lineChartData
    )
}

private fun formatToTime(time: LocalDateTime, period: GraphPeriods): String {
    return when(period) {
        GraphPeriods.Hours -> time.truncatedTo(ChronoUnit.HOURS).toLocalTime().toString()
        GraphPeriods.Months -> time.month.toString()
        GraphPeriods.Years -> time.year.toString()
    }
}

private fun getTimeBefore(time: LocalDateTime, period: GraphPeriods): LocalDateTime {
    return when(period) {
        GraphPeriods.Hours -> { time.minusHours(1) }
        GraphPeriods.Months -> { time.minusMonths(1) }
        GraphPeriods.Years -> { time.minusYears(1) }
    }
}

private fun getTimeAfter(time: LocalDateTime, period: GraphPeriods): LocalDateTime {
    return when(period) {
        GraphPeriods.Hours -> { time.plusHours(1) }
        GraphPeriods.Months -> { time.plusMonths(1) }
        GraphPeriods.Years -> { time.plusYears(1) }
    }
}



