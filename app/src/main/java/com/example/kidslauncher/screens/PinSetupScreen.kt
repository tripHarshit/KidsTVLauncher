package com.example.kidslauncher.screens

import androidx.compose.material.OutlinedTextField
import androidx.tv.material3.Text
import com.example.kidslauncher.models.PinManager


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.kidslauncher.MainActivity

@Composable
fun PinSetupScreen(context: Context) {
    var pin by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Set a PIN to exit Kids Launcher")

        OutlinedTextField(
            value = pin,
            onValueChange = { pin = it },
            label = { Text("Enter PIN") }
        )

        Button(
            onClick = {
                if (pin.length >= 4) {
                    PinManager.savePin(context, pin)
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save PIN")
        }
    }
}
