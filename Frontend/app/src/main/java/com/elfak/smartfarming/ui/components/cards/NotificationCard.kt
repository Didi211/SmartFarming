
package com.elfak.smartfarming.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.Notification
import com.elfak.smartfarming.data.models.formatDate
import com.elfak.smartfarming.ui.components.buttons.DeleteIconButton
import com.elfak.smartfarming.ui.theme.BorderColor
import com.elfak.smartfarming.ui.theme.ErrorColor
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun NotificationCard(
    notification: Notification,
    onDelete: (id: String) -> Unit
) {
    Box {
        CurvedBottomBorderCard {
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
                                    "OFFLINE" -> ErrorColor
                                    else -> FontColor
                                }
                                Text(
                                    text = notification.deviceStatus,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = color
                                )
                            }

                            Spacer(Modifier.height(10.dp))
                            // message
                            Text(
                                text = notification.message,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "From: ${notification.createdAt.formatDate()}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "To: ${notification.updatedAt.formatDate()}",
                                style = MaterialTheme.typography.bodyMedium
                            )
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



