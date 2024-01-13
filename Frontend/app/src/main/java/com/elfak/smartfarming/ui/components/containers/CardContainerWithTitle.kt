package com.elfak.smartfarming.ui.components.containers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun CardContainerWithTitle(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.wrapContentSize()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Divider(color = FontColor, modifier = Modifier.padding(bottom = 10.dp))
        content()
    }
}