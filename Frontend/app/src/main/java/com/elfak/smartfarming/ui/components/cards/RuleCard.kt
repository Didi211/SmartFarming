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
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIcon
import com.elfak.smartfarming.ui.components.menu.Menu
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun RuleCard(
    rule: Rule,
    menuItems: List<MenuItem> = emptyList(),
    onCardClick: (id: String) -> Unit,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Box {
        CurvedBottomBorderCard (
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .clickable { onCardClick(rule.id) }
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
                            .fillMaxWidth(0.9f)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = rule.name.uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(7.35f.dp))
                        // rule description
                        Text(
                            text = rule.description,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = FontColor
                        )
                    }

                    // buttons
                    Column(
                        Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
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
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RuleCardPreview() {
//    SmartFarmingTheme {
//        Column(Modifier.fillMaxSize().padding(10.dp)) {
//            RuleCard(
//                rule = Rule(
//                    id = "id",
//                    name = "Rule - 1",
//                    description = "Start the actuator when start trigger level is < than 34. Stop the actuator when stop trigger level is > than 60",
//                ),
//                onDelete = { },
//                onEdit = { },
//                onCardClick = { }
//            )
//        }
//    }
//}