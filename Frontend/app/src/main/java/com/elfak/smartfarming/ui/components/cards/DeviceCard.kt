package com.elfak.smartfarming.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.uppercase
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIcon
import com.elfak.smartfarming.ui.components.menu.Menu
import com.elfak.smartfarming.ui.theme.ErrorColor
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun DeviceCard(
    device: Device,
    showMenu: Boolean = true,
    menuItems: List<MenuItem> = emptyList(),
    onBellIconClick: (id: String) -> Unit = { },
    onCardClick: (id: String) -> Unit = { },
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Box {
        CurvedBottomBorderCard(modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .clickable { onCardClick(device.id) }
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.8f)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        // title
                        Row {
                            Text(
                                text = "${device.name} - " ,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            val color = when (device.status) {
                                DeviceStatus.Offline -> ErrorColor
                                DeviceStatus.Online -> FontColor
                            }
                            Text(
                                text = device.status.uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = color
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Type: ${device.type}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = FontColor
                        )
                        if (device.type == DeviceTypes.Sensor) {
                            Text(
                                text = "Unit: ${device.unit}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = FontColor
                            )
                            Text(
                                text = "Last reading: ${device.lastReading}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = FontColor
                            )
                        }
                        else {
                            Text(
                                text = "State: ${device.state}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = FontColor
                            )

                        }
                    }

                    // buttons
                    Box {
                        Column(
                            Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (showMenu) {
                                Box {
                                    ButtonWithIcon(
                                        icon = Icons.Rounded.MoreVert,
                                        backgroundColor = Color.Transparent,
                                        iconColor = FontColor,
                                        size = 35.dp
                                    ) {
                                        isMenuExpanded = true
                                    }
                                    Menu(
                                        expanded = isMenuExpanded,
                                        menuItems = menuItems,
                                        onDismissRequest = { isMenuExpanded = false },
                                        onIconClick = { isMenuExpanded = false }
                                    )
                                }
                            }
                            val notifyIcon = when (device.isMuted) {
                                true -> Icons.Outlined.NotificationsOff
                                false -> Icons.Outlined.Notifications
                            }
                            ButtonWithIcon(
                                icon = notifyIcon,
                                backgroundColor = Color.Transparent,
                                iconColor = FontColor,
                                size = 35.dp
                            ) {
                                onBellIconClick(device.id)
                            }
                        }
                    }
                }
            }
        }
    }
}