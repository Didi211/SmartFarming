package com.elfak.smartfarming.data.models

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerMenuItem(
    val name: String,
    val icon: ImageVector,
    val action: () -> Unit
)
