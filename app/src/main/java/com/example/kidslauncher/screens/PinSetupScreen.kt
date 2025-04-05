package com.example.kidslauncher.screens


import androidx.tv.material3.Text
import com.example.kidslauncher.models.PinManager


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
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

@Composable
fun PinSetupScreen(context: Context) {
    var pin by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFDE7)), // Soft creamy yellow background
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .background(Color(0xFFFFEB3B), shape = RoundedCornerShape(20.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Set a PIN to exit Kids Launcher",
                fontSize = 20.sp,
                color = Color(0xFF4A148C),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pin,
                onValueChange = { pin = it },
                label = {
                    Text(
                        "Enter PIN",
                        color = Color(0xFF4A148C)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (pin.length >= 4) {
                        PinManager.savePin(context, pin)
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Text("Save PIN", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

