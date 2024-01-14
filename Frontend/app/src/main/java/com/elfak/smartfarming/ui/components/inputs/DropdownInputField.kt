package com.elfak.smartfarming.ui.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.R
import com.elfak.smartfarming.ui.theme.BorderColor
import com.elfak.smartfarming.ui.theme.Disabled
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.Placeholder

@Composable
fun DropdownInputField(
    modifier: Modifier = Modifier,
    items: List<String> = emptyList(),
    value: String,
    defaultText: String = "Choose item",
    enabled: Boolean = true,
    onSelect: (String) -> Unit = { },
) {
    var isSelected by remember {
        mutableStateOf(value.isNotBlank()) }
    var showDropdown by remember { mutableStateOf(false) }

    Row(
        modifier = if (enabled) modifier.clickable { showDropdown = !showDropdown } else modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(start = 15.dp),
            text = if (isSelected) value else  defaultText,
            color = if (enabled) FontColor else Placeholder
        )
        Icon(
            imageVector = Icons.Rounded.ArrowDropDown,
            contentDescription = stringResource(id = R.string.choose_dropdown_item),
            tint = if (enabled) BorderColor else Disabled,
            modifier = Modifier.fillMaxSize()
        )
        DropdownMenu(
            modifier = Modifier
                .heightIn(max = 300.dp)
                .fillMaxWidth(0.7f),
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false }
        ) {
            for (item in items) {
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        isSelected = true
                        onSelect(item)
                        showDropdown = false
                    })
            }
        }
    }
}