package com.example.kidslauncher.utils
import androidx.compose.material3.AlertDialog


import androidx.compose.runtime.Composable
import androidx.tv.material3.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ShowExitDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Exit Launcher") },
            text = { Text("Enter PIN to exit") },
            confirmButton = {
                Button(onClick = { /* Verify PIN and exit */ }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
            }
        )
    }
}
