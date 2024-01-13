package com.elfak.smartfarming.ui.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIconAndText

@Composable
fun SmartFarmingBottomAppBar(
    buttons: List<MenuItem>
) {
    BottomAppBar(
        modifier = Modifier
            .height(60.dp)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary

    ) {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(buttons) { item ->
                ButtonWithIconAndText(
                    modifier = Modifier.fillMaxSize(),
                    text = item.name,
                    onClick = item.action,
                    icon = item.icon
                )

            }
        }
    }
}