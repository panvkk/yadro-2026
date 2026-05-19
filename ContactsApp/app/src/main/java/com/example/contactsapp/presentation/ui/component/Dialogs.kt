package com.example.contactsapp.presentation.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.contactsapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDeniedAlertDialog(
    onDismiss: () -> Unit,
    text: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(R.string.confirm_text_button),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.default_alert_dialog_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        },
    )
}