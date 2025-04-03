package com.example.kidslauncher

import androidx.compose.material.OutlinedTextField
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.example.kidslauncher.models.PinManager
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kidslauncher.MainActivity

class PinScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinVerificationScreen(this)
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PinVerificationScreen(context: Context) {
    var enteredPin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Enter PIN to exit")

        OutlinedTextField(
            value = enteredPin,
            onValueChange = { enteredPin = it },
            label = { Text("Enter PIN") }
        )

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = androidx.compose.ui.graphics.Color.Red)
        }

        Button(
            onClick = {
                if (enteredPin == PinManager.getPin(context)) {
                    (context as? ComponentActivity)?.finishAffinity()

                } else {
                    errorMessage = "Incorrect PIN. Try again."
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Verify")
        }
    }
}
