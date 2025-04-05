package com.example.kidslauncher

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kidslauncher.models.PinManager
import com.example.kidslauncher.screens.KidsLauncherScreen
import com.example.kidslauncher.screens.PinSetupScreen
import com.example.kidslauncher.utils.getAllInstalledApps
import com.example.kidslauncher.utils.preloadApprovedApps
import com.example.kidslauncher.utils.saveApprovedApps

class MainActivity : ComponentActivity() {
    private lateinit var sharedPref: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val allApps = getAllInstalledApps(this)
        allApps.forEach {
            Log.d("INSTALLED_APP", "Name: ${it.name}, Package: ${it.packageName}")
        }

        // ✅ Preload apps if not already set
        val sharedPreferences = getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
        val approvedApps = sharedPreferences.getStringSet("approved_apps", null)
        if (approvedApps == null || approvedApps.isEmpty()) {
            preloadApprovedApps(this)
        }

        setContent {
            if (PinManager.isPinSet(this)) {
                preloadApprovedApps(this)
                KidsLauncherScreen()
            } else {
                PinSetupScreen()
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
