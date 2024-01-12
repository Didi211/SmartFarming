@file:OptIn(ExperimentalMaterialApi::class)

package com.elfak.smartfarming.ui.components.containers

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.ui.theme.FontColor

@Composable
fun RuleTab(
    rules: List<Rule>,
    refreshState: PullRefreshState,
    isRefreshing: Boolean,
    onCardClick: (id: String) -> Unit = { },
    onDelete: (id: String) -> Unit = { },
    onEdit: (id: String) -> Unit = { },
) {
    AnimatedContent(rules.isEmpty(), label = "") { empty ->
        when (empty) {
            true -> {
                PullRefreshContainer(
                    refreshState = refreshState,
                    isRefreshing = isRefreshing
                ) {
                    Text(
                        "No rules.",
                        style = MaterialTheme.typography.headlineMedium,
                        color = FontColor,
                    )
                }
            }
            false -> {
                RulesCardContainer(
                    rules = rules,
                    refreshState = refreshState,
                    isRefreshing = isRefreshing,
                    onDelete = onDelete,
                    onEdit = onEdit,
                    onCardClick = onCardClick
                )
            }
        }
    }
}

