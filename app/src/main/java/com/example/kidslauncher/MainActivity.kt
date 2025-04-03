package com.example.kidslauncher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kidslauncher.models.PinManager
import com.example.kidslauncher.screens.KidsLauncherScreen
import com.example.kidslauncher.screens.PinSetupScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            if (PinManager.isPinSet(this)) {
                KidsLauncherScreen()
            } else {
                PinSetupScreen(this)
            }
        }
    }

    override fun onBackPressed() {
        Log.d("BackPress", "Navigating to PIN screen")
        val intent = Intent(this, PinScreenActivity::class.java)
        startActivity(intent)
        finish()
    }
}

