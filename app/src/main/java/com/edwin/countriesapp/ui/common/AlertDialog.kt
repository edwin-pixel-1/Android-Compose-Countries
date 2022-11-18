package com.edwin.countriesapp.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AlertDialogExample() {
    val openDialog = remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = {
                openDialog.value = true
            }
        ) {
            Text(text = "Show dialog")
        }
        if (openDialog.value) {
            AlertDialogCustom(
                title = "Edwin's dialog",
                text = "This is just a dialog",
                onPositive = { openDialog.value = false },
                onNegative = { openDialog.value = false }
            )
        }
    }
}

@Composable
fun AlertDialogCustom(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    onPositive: () -> Unit,
    onNegative: (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = { /* no-op */ },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        confirmButton = {
            Button(
                onClick = onPositive
            ) {
                Text(text = "Ok")
            }
        },

        dismissButton = if (onNegative != null) {
            {
                Button(
                    onClick = { onNegative.invoke() }
                ) {
                    Text(text = "Cancel")
                }
            }
        } else null,

        modifier = modifier
    )
}
