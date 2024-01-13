package com.elfak.smartfarming.data.models

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val name: String,
    val icon: ImageVector,
    val action: () -> Unit
)
