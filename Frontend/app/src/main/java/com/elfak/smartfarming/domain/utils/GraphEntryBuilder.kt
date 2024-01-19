package com.elfak.smartfarming.domain.utils

import android.os.Build
import com.elfak.smartfarming.data.models.GraphReading
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class GraphEntryBuilder {
    companion object {
        fun createEntries(readings: List<GraphReading>): ChartEntryModel {
//            val data = readings
//                .map { it.timeMeasured to it.reading.toFloat() } // x and y values for graph
//                .associate { (date, yValue) -> date to yValue }

//            // converting data to float value
//            val entries = mutableListOf<FloatEntry>()
//
//            data.forEach { (time, reading) ->
//                entries.add( FloatEntry(
//                    x = time.truncatedTo(ChronoUnit.HOURS).toEpochSecond(ZoneOffset.UTC).toFloat(),
//                    y = reading
//                ))
//            }
//            return entryModelOf(entries)

//            val xValuesToDates = data.keys.associateBy {
//                it.toEpochSecond(ZoneOffset.UTC).toFloat()
//            }
//            val chartEntryModel = entryModelOf(xValuesToDates.keys.zip(data.values, ::entryOf))
//            return chartEntryModel

            val entries = readings.map { (time, reading) ->
                FloatEntry(
                    x = time.toInstant(ZoneOffset.UTC).toEpochMilli().toFloat(),
                    y = reading.toFloat()
                )
            }
            return entryModelOf(entries)
        }
    }
}