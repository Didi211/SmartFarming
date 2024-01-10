package com.elfak.smartfarming.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.domain.enums.InputTypes
import com.elfak.smartfarming.ui.theme.Border
import com.elfak.smartfarming.ui.theme.Disabled
import com.elfak.smartfarming.ui.theme.FontColor
import com.elfak.smartfarming.ui.theme.Placeholder
import com.elfak.smartfarming.ui.theme.SmartFarmingTheme

@Composable
fun BasicInputField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    inputType: InputTypes = InputTypes.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    placeholder: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    ) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        label = {
            Text(
                modifier = Modifier.background(color = Color.Transparent),
                text = label,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        onValueChange = onTextChanged,
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = getColors(),
        shape = RoundedCornerShape(30.dp),
        enabled = enabled,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (inputType == InputTypes.Password) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardActions = keyboardActions,
        trailingIcon = trailingIcon,
        placeholder = { Text(text = placeholder) }
    )

}

@Composable
fun getColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        // container
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        disabledContainerColor = Disabled,
        // label
        focusedLabelColor = FontColor,
        unfocusedLabelColor = FontColor,
        disabledLabelColor = FontColor,
        // text
        focusedTextColor = FontColor,
        unfocusedTextColor = FontColor,
        disabledTextColor = FontColor,
        // border
        focusedBorderColor = Border,
        unfocusedBorderColor = Border,
        disabledBorderColor = Border,
        // placeholder
        focusedPlaceholderColor = Placeholder,
        unfocusedPlaceholderColor = Placeholder


    )
}

//@Preview(showBackground = true)
//@Composable
//fun BasicInputFieldPreview() {
//    SmartFarmingTheme {
//        BasicInputField(
//            text = "Text",
//            onTextChanged = { },
//            label = "Label",
//            placeholder = "placeholder"
//        )
//    }
//}