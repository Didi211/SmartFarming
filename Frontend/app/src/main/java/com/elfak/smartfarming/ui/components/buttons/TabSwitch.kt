package com.elfak.smartfarming.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.Secondary
import com.elfak.smartfarming.ui.theme.SmartFarmingTheme

@Composable
fun TabSwitch(
    leftTabTitle: String,
    rightTabTitle: String,
    activeTab: String,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .height(40.dp)
//            .padding(horizontal = 10.dp)
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // left tab
        Tab(
            shape = RoundedCornerShape(
              topStart = 10.dp, bottomStart = 10.dp,
            ),
            rowScope = this,
            title = leftTabTitle,
            isSelected = activeTab.lowercase() == leftTabTitle.lowercase()
        )
        // right tab
        Tab(
            shape = RoundedCornerShape(
                topEnd = 10.dp, bottomEnd = 10.dp,
            ),
            rowScope = this,
            title = rightTabTitle,
            isSelected = activeTab.lowercase() == rightTabTitle.lowercase()
        )
    }
}

@Composable
fun Tab(rowScope: RowScope, shape: Shape, title: String, isSelected: Boolean) {
    val containerColor = if (isSelected) Secondary else Color.White
    val textColor = if (isSelected) Color.White else FontColor
    rowScope.apply {
        Row(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .fillMaxWidth()
                .background(containerColor, shape = shape)
                .border(2.dp, color = Secondary, shape = shape)
                ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabSwitchPreview() {
    SmartFarmingTheme {
        Row(Modifier.fillMaxSize().padding(15.dp)) {
            TabSwitch(leftTabTitle = "Left", rightTabTitle = "Right", activeTab = "Left") {

            }

        }
    }
}