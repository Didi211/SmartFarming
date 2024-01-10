package com.elfak.smartfarming.ui.components.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.elfak.smartfarming.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartFarmingTopAppBar(
    title: String,
    onIconClick: () -> Unit = {  }
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = onIconClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "navigation"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
        ),
    )
}