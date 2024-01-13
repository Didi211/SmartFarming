package com.elfak.smartfarming.ui.components.menu

import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun Menu(
    expanded: Boolean,
    menuItems: List<MenuItem>,
    onDismissRequest: () -> Unit,
    onIconClick: () -> Unit
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        menuItems.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
                },
                onClick = {
                    onIconClick()
                    item.action()
                },
                leadingIcon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = MenuDefaults.itemColors(
                    textColor = FontColor,
                    leadingIconColor = FontColor
                )
            )
        }
    }
}