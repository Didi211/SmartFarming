package com.elfak.smartfarming.ui.components.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.elfak.smartfarming.R

@Composable
fun HandPlantImage(modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.hand_plant),
            contentDescription = stringResource(id = R.string.hand_plant_description),
        )
    }
}