package com.elfak.smartfarming.ui.screens.deviceDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cable
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.R
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.domain.enums.ScreenState
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIcon
import com.elfak.smartfarming.ui.components.buttons.ButtonWithIconAndText
import com.elfak.smartfarming.ui.components.cards.RuleCard
import com.elfak.smartfarming.ui.components.containers.CardContainerWithTitle

@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsScreenViewModel,
    navigateBack: () -> Unit = { },
    navigateToRuleDetails: (ruleId: String?, screenState: ScreenState) -> Unit,
) {
    ToastHandler(
        toastData = viewModel.uiState.toastData,
        clearErrorMessage = viewModel::clearErrorMessage,
        clearSuccessMessage = viewModel::clearSuccessMessage
    )

    Column(
        Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState(1000), true, null, true),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // device details
        Column(Modifier.wrapContentSize()) {
            Row(horizontalArrangement = Arrangement.End) {
                ButtonWithIcon(icon = Icons.Rounded.Edit, iconColor = MaterialTheme.colorScheme.secondary) {
                    
                }
            }
            Column(
                Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .background(Color.White)
                    .wrapContentSize()
            ) {

            }
            val buttonColor = when(viewModel.uiState.screenState) {
                ScreenState.Edit -> MaterialTheme.colorScheme.secondary
                ScreenState.View -> MaterialTheme.colorScheme.error
                ScreenState.Create -> MaterialTheme.colorScheme.error
            }
            val buttonText = when(viewModel.uiState.screenState) {
                ScreenState.Edit -> stringResource(R.string.save_changes)
                ScreenState.View -> stringResource(R.string.delete_device)
                ScreenState.Create -> stringResource(R.string.save_changes)
            }
            val buttonIcon = when(viewModel.uiState.screenState) {
                ScreenState.Edit -> Icons.Rounded.Check
                ScreenState.View -> Icons.Rounded.Delete
                ScreenState.Create -> Icons.Rounded.Check
            }
            ButtonWithIconAndText(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .width(180.dp)
                    .height(60.dp)
                    .background(buttonColor),
                text = buttonText,
                onClick = {  },
                icon = buttonIcon
            )


        }
        Spacer(modifier = Modifier.height(40.dp))
        // related rule
        CardContainerWithTitle(title = stringResource(R.string.related_rule)) {
            if (viewModel.uiState.rule != null) {
                // rule card
                val ruleId = viewModel.uiState.rule!!.id
                RuleCard(
                    viewModel.uiState.rule!!,
                    showMenu = false,
                    onCardClick = { navigateToRuleDetails(ruleId, ScreenState.View) }
                )
                // Add Rule button
                Column(
                    Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonWithIconAndText(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .width(180.dp)
                            .height(60.dp)
                            .background(MaterialTheme.colorScheme.error),
                        text = stringResource(R.string.delete_rule),
                        onClick = { viewModel.deleteRule(ruleId) },
                        icon = Icons.Rounded.Delete
                    )
                }
            }
            else {
                // Add Rule button
                Column(
                    Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text = stringResource(R.string.no_existing_rule_for_this_sensor),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonWithIconAndText(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .width(180.dp)
                            .height(60.dp)
                            .background(MaterialTheme.colorScheme.secondary),
                        text = stringResource(R.string.add_rule),
                        onClick = { navigateToRuleDetails(null, ScreenState.Create) },
                        icon = Icons.Rounded.Cable
                    )
                }
            }
        }

    }
}

private fun prepareMenuItems(
    id: String,
    onEdit: (id: String, screenState: ScreenState) -> Unit,
    onDelete: (id: String) -> Unit
): List<MenuItem> {
    return listOf(
        MenuItem("Edit", Icons.Rounded.Edit, action = { onEdit(id, ScreenState.Edit) }),
        MenuItem("Delete", Icons.Rounded.Delete, action = { onDelete(id) })
    )
}
