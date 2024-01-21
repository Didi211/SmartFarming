package com.elfak.smartfarming.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.ui.components.graphs.LineGraphChart

@Composable
fun GraphCard(
    sensor: Device,
    readings: List<GraphReading> = emptyList(),
    onCardClick: (deviceId: String) -> Unit
) {
    Column(
        Modifier
            .padding(10.dp)
            .height(230.dp)
            .fillMaxWidth(0.9f)
            .clickable { onCardClick(sensor.id) },
    ) {
        Text(
            text = sensor.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Column(Modifier.fillMaxSize()) {
            LineGraphChart(readings)
        }
    }
}