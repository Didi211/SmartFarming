package com.elfak.smartfarming.ui.screens.registerScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.elfak.smartfarming.R
import com.elfak.smartfarming.domain.enums.InputTypes
import com.elfak.smartfarming.ui.components.ToastHandler
import com.elfak.smartfarming.ui.components.buttons.BasicButton
import com.elfak.smartfarming.ui.components.images.LogoImage
import com.elfak.smartfarming.ui.components.inputs.BasicInputField
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel,
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit,
) {

    val focusManager = LocalFocusManager.current
    var inProgress by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // error handler
    ToastHandler(
        toastData = viewModel.uiState.toastData,
        clearErrorMessage = {
            viewModel.clearErrorMessage()
            inProgress = false
        },
        clearSuccessMessage = viewModel::clearSuccessMessage
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // back button
        IconButton(
            modifier = Modifier.padding(15.dp),
            onClick = navigateBack) {
            Icon(
                Icons.Rounded.ArrowBack,
                stringResource(id = R.string.back_arrow_button),
                modifier = Modifier.size(45.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            // logo
            LogoImage(size = 200.dp)
            Spacer(modifier = Modifier.height(30.dp))

            // inputs
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
//                    .verticalScroll(rememberScrollState())
                    .verticalScroll(rememberScrollState(1000), true, null, true)
                    .weight(1f, fill = false)
            ) {
                // name
                BasicInputField(
                    text = viewModel.uiState.name,
                    onTextChanged = {
                        viewModel.setName(it)
                    },
                    enabled = !inProgress,
                    label = stringResource(id = R.string.name) + ":",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    inputType = InputTypes.Text,
                    placeholder = "John Doe"
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                // email
                BasicInputField(
                    text = viewModel.uiState.email,
                    onTextChanged = {
                        viewModel.setEmail(it.trim())
                    },
                    enabled = !inProgress,
                    label = stringResource(id = R.string.email) + ":",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    inputType = InputTypes.Text,
                    placeholder = "email@domain.com"
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                // password
                BasicInputField(
                    text = viewModel.uiState.password,
                    onTextChanged = {
                        viewModel.setPassword(it.trim())
                    },
                    enabled = !inProgress,
                    label = stringResource(id = R.string.password) + ":",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                    ),
                    inputType = InputTypes.Password
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                // confirm password
                BasicInputField(
                    text = viewModel.uiState.confirmPassword,
                    onTextChanged = {
                        viewModel.setConfirmPassword(it.trim())
                    },
                    enabled = !inProgress,
                    label = stringResource(id = R.string.confirm_password) + ":",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            inProgress = true
                            focusManager.clearFocus()
                            coroutineScope.launch {
                                viewModel.register {
                                    inProgress = false
                                    navigateToHome()
                                }
                            }
                        }
                    ),
                    inputType = InputTypes.Password
                )
                Spacer(modifier = Modifier.heightIn(30.dp))

                //buttons
                if (inProgress) {
                    CircularProgressIndicator()
                }
                else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // login button
                        BasicButton(text = "REGISTER") {
                            inProgress = true
                            focusManager.clearFocus()
                            coroutineScope.launch {
                                viewModel.register {
                                    inProgress = false
                                    navigateToHome()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}