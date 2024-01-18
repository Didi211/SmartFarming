package com.elfak.smartfarming.domain.utils.graphFormatters

import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class AxisHourFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    override fun formatValue(value: Float, chartValues: ChartValues): CharSequence {
        // convert int value of time to time
        val time = LocalDateTime.ofEpochSecond(value.toLong(), 0, ZoneOffset.UTC).toLocalTime()
        return time.truncatedTo(ChronoUnit.MINUTES).toString()
    }
}
class AxisMonthFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    override fun formatValue(value: Float, chartValues: ChartValues): CharSequence {
        // convert int value of time to time
        val time = LocalDateTime.ofEpochSecond(value.toLong(), 0, ZoneOffset.UTC).month
        return time.toString()
    }
}
class AxisYearFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    override fun formatValue(value: Float, chartValues: ChartValues): CharSequence {
        // convert int value of time to time
        val time = LocalDateTime.ofEpochSecond(value.toLong(), 0, ZoneOffset.UTC).year
        return time.toString()
    }
}
