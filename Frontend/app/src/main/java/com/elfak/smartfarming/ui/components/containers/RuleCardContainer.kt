@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.components.containers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.data.models.MenuItem
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.ui.components.cards.RuleCard

@Composable
fun RulesCardContainer(
    rules: List<Rule>,
    refreshState: PullRefreshState,
    isRefreshing: Boolean,
    onDelete: (id: String) -> Unit = { },
    onEdit: (id: String, editMode: Boolean?) -> Unit = { _, _ -> },
    onCardClick: (id: String, editMode: Boolean?) -> Unit = { _, _ -> },
) {
    Box(modifier = Modifier.pullRefresh(refreshState)) {
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
//            contentPadding = PaddingValues(15.dp)
        ) {
            items (rules) { rule ->
                RuleCard(
                    rule = rule,
                    menuItems = prepareMenuItems(rule.id, onEdit, onDelete),
                    onCardClick = { onCardClick(rule.id, null) },
                )
            }
        }
        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}

private fun prepareMenuItems(
    ruleId: String,
    onEdit: (id: String, editMode: Boolean) -> Unit,
    onDelete: (id: String) -> Unit
): List<MenuItem> {
    return listOf(
        MenuItem("Edit", Icons.Rounded.Edit, action = { onEdit(ruleId, true) }),
        MenuItem("Delete", Icons.Rounded.Delete, action = { onDelete(ruleId) })
    )
}