package com.elfak.smartfarming.ui.components.cards

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.ui.theme.BorderColor

@Composable
fun CurvedBottomBorderCard( modifier: Modifier = Modifier, height: Dp = 130.dp, content: @Composable ColumnScope.() -> Unit) {
    val shape = RoundedCornerShape(30.dp)
    val elevation = CardDefaults.cardElevation(
        defaultElevation = 5.dp
    )

    Card(
        shape = shape,
        elevation = elevation,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .offset(y = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = BorderColor,
        ),
    ) { }

    Card(
        shape = shape,
        elevation = elevation,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        content()
    }
}