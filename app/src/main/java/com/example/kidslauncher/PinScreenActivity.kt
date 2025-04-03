package com.example.kidslauncher

import android.app.Activity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.example.kidslauncher.models.PinManager
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.kidslauncher.MainActivity

class PinScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinVerificationScreen()
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PinVerificationScreen() {
    val context = LocalContext.current
    var enteredPin by remember { mutableStateOf("") }
    val storedPin = PinManager.getPin(context) ?: ""

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Enter your PIN to exit Kids Launcher")

        OutlinedTextField(
            value = enteredPin,
            onValueChange = { enteredPin = it },
            label = { Text("Enter PIN") }
        )

        Button(
            onClick = {
                Log.d("PinScreen", "Button clicked with PIN: $enteredPin")
                if (enteredPin == storedPin) {
                    Log.d("PinScreen", "PIN verified successfully")
                    (context as? Activity)?.finishAffinity() // Closes the app completely
                } else {
                    Log.d("PinScreen", "Incorrect PIN, navigating back to home screen")
                    context.startActivity(Intent(context, MainActivity::class.java))
                    Toast.makeText(context,"WRONG PIN!! Exit Revoked", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Verify")
        }


    }
}
