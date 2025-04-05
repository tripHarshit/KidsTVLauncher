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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF176))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF176), shape = RoundedCornerShape(24.dp))
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🔐 Enter PIN to Exit",
                fontSize = 24.sp,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = enteredPin,
                onValueChange = { enteredPin = it },
                label = {
                    Text(
                        text = "Enter PIN",
                        color = Color(0xFF2E7D32),
                        fontSize = 16.sp
                    )
                },
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF9C4), RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (enteredPin == storedPin) {
                        (context as? Activity)?.finishAffinity()
                    } else {
                        context.startActivity(Intent(context, MainActivity::class.java))
                        Toast.makeText(context, "WRONG PIN!! Exit Revoked", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF5350),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Verify", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(15.dp))

            // New Cancel Button
            Text(
                text = "Cancel",
                fontSize = 18.sp,
                color = Color(0xFF2E7D32),
                modifier = Modifier
                    .clickable {
                        // Return to main launcher screen
                        context.startActivity(
                            Intent(context, MainActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                        (context as? Activity)?.finish()
                    }
                    .padding(8.dp)
            )
        }
    }
}

