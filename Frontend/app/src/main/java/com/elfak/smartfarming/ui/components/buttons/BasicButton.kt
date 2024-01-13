package com.elfak.smartfarming.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.ui.theme.SmartFarmingTheme

@Composable
fun BasicButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.width(150.dp).height(50.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BasicButtonPreview() {
    SmartFarmingTheme {
        BasicButton(text = "Text") {

        }

    }
}
