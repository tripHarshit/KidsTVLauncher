package com.example.kidslauncher

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat.getSystemService
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
    override fun onResume() {
        super.onResume()
        val runningApps = getRunningApps(this)
        if (runningApps.contains("com.android.settings")) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}


@SuppressLint("WrongConstant")
private fun getRunningApps(context: Context): List<String> {
    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val time = System.currentTimeMillis()

    val appList = usageStatsManager.queryUsageStats(
        UsageStatsManager.INTERVAL_DAILY,
        time - 1000 * 10,
        time
    )

    return appList?.sortedByDescending { it.lastTimeUsed }
        ?.map { it.packageName }
        ?.distinct()
        ?: emptyList()
}


