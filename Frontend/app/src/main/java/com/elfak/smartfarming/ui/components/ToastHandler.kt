package com.elfak.smartfarming.ui.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.elfak.smartfarming.data.models.ToastData
import es.dmoral.toasty.Toasty


@Composable
fun ToastHandler(
    toastData: ToastData,
    clearErrorMessage: () -> Unit,
    clearSuccessMessage: () -> Unit,
) {
    if (toastData.hasErrors) {
        Toasty.error(LocalContext.current, toastData.errorMessage, Toast.LENGTH_LONG, true).show()
        clearErrorMessage()
    }
    if (toastData.hasSuccessMessage) {
        Toasty.info(LocalContext.current, toastData.successMessage, Toast.LENGTH_SHORT, false).show()
        clearSuccessMessage()
    }

}
