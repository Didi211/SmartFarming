
package com.elfak.smartfarming.ui.components.cards

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Notification
import com.elfak.smartfarming.data.models.formatDate
import com.elfak.smartfarming.domain.enums.DeviceStatus
import com.elfak.smartfarming.ui.components.buttons.DeleteIconButton
import com.elfak.smartfarming.ui.theme.ErrorColor
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.SmartFarmingTheme

@Composable
fun NotificationCard(
    notification: Notification,
    onDelete: (id: String) -> Unit
) {
    Box {
        CurvedBottomBorderCard(height = 150.dp) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ) {
                        // notification details
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
                                    text = "${notification.title() ?: "No_title"} - " ,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                val color = when (notification.deviceStatus) {
                                    DeviceStatus.Offline -> ErrorColor
                                    DeviceStatus.Online -> FontColor
                                }
                                Text(
                                    text = notification.deviceStatus.name.uppercase(),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = color
                                )
                            }

                            Spacer(Modifier.height(10.dp))
                            // message
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = notification.message,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            //
                            Row {
                                Column {
                                    Text(
                                        text = "From:",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "To:",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Spacer(Modifier.width(5.dp))
                                Column {
                                    Text(
                                        text = notification.createdAt.formatDate(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = notification.updatedAt.formatDate(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                        Box {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                DeleteIconButton { onDelete(notification.id) }
                            }
                        }
                    }
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationCardPreview() {
    val notif = Notification(
        id = "65a1e1447f0f3b9d576f0d8d",
        message = "Device [test-4] stopped working.",
        createdAt = "2024-01-13T01:03:00.920+00:00",
        updatedAt = "2024-01-13T01:12:00.608+00:00"
    )
    Column {
        SmartFarmingTheme {
            NotificationCard(notification = notif, onDelete = { })
        }
    }
}

