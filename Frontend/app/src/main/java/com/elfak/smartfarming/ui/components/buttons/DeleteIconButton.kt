package com.elfak.smartfarming.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.R

@Composable
fun DeleteIconButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(
                CircleShape,
            )
            .background(MaterialTheme.colorScheme.error)
            .padding(5.dp)
            .clickable { onClick() },
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.delete_button),
            modifier = Modifier.size(25.dp),
            tint = MaterialTheme.colorScheme.onError
        )

    }
}

//@Preview
//@Composable
//fun DeleteIconPreview() {
//    SmartFarmingTheme {
//        DeleteIconButton {
//
//        }
//    }
//}