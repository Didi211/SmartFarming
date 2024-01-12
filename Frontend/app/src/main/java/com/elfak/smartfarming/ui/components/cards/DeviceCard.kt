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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.domain.enums.DeviceState
import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.uppercase
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIcon
import com.elfak.smartfarming.ui.theme.ErrorColor
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.SmartFarmingTheme

@Composable
fun DeviceCard(
    device: Device,
    onBellIconClick: (id: String) -> Unit,
    onDelete: (id: String) -> Unit,
    onCardClick: (id: String) -> Unit,
    onEdit: (id: String) -> Unit,
) {
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
                        if (device.unit != null) {
                            Text(
                                text = "Unit: ${device.unit}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = FontColor
                            )
                            // TODO - add last reading from real time data
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
                            ButtonWithIcon(
                                icon = Icons.Rounded.MoreVert,
                                backgroundColor = Color.Transparent,
                                iconColor = FontColor,
                                size = 35.dp
                            ) {
                                // show menu 
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

@Preview(showBackground = true)
@Composable
fun DeviceCardPreview() {
    SmartFarmingTheme {
        DeviceCard(device = Device(
            id = "65a0467e326ca03fd48b0a5d",
            name = "test-1",
            type = DeviceTypes.Actuator,
            status = DeviceStatus.Online,
            state = DeviceState.Off
        ), onDelete = { }, onCardClick = { }, onEdit = { }, onBellIconClick = { } )
    }
}