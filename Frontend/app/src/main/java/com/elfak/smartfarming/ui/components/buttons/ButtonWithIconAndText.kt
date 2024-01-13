package com.elfak.smartfarming.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.R

@Composable
fun ButtonWithIconAndText(
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    text: String,
    onClick: () -> Unit,
    icon: ImageVector
) {
    Column(
        modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            stringResource(id = R.string.bottom_bar_action),
            modifier = Modifier.size(25.dp),
            tint = textColor
        )
        Text(
            text = text,
            color = textColor
        )
    }
}