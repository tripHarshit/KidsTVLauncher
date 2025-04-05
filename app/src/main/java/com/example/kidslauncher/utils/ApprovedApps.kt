package com.example.kidslauncher.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.edit
import com.example.kidslauncher.models.AppInfo

fun getApprovedApps(context: Context): List<AppInfo> {
    val packageManager = context.packageManager
    val sharedPreferences = context.getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
    val approvedPackages = sharedPreferences.getStringSet("approved_apps", emptySet()) ?: emptySet()

    val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    return installedApps.mapNotNull { app ->
        if (approvedPackages.contains(app.packageName)) {
            try {
                val label = packageManager.getApplicationLabel(app).toString()
                val icon = packageManager.getApplicationIcon(app.packageName)
                Log.d("APP_DEBUG", "Loaded app: ${app.packageName}")
                AppInfo(name = label, packageName = app.packageName, icon = icon)
            } catch (e: Exception) {
                Log.e("APP_DEBUG", "Error loading app: ${app.packageName}", e)
                null
            }
        } else {
            null
        }
    }
}




fun addApprovedApp(context: Context, packageName: String) {
    val sharedPreferences = context.getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
    val approvedPackages = sharedPreferences.getStringSet("approved_apps", emptySet())?.toMutableSet() ?: mutableSetOf()

    approvedPackages.add(packageName)

    sharedPreferences.edit {
        putStringSet("approved_apps", approvedPackages)
    }

    val intent = Intent("com.example.kidslauncher.UPDATE_APPROVED_APPS")
    context.sendBroadcast(intent)
}


fun saveApprovedApps(context: Context, approvedApps: List<String>) {
    val sharedPreferences = context.getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit {
        putStringSet("approved_apps", approvedApps.toSet())
        apply()
    }

    val intent = Intent("com.example.kidslauncher.UPDATE_APPROVED_APPS")
    context.sendBroadcast(intent)
}


fun preloadApprovedApps(context: Context) {
    val sharedPreferences = context.getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
    val alreadyPreloaded = sharedPreferences.getBoolean("apps_preloaded", false)

    if (!alreadyPreloaded) {
        val packageManager = context.packageManager
        val allLaunchablePackages = getAllInstalledApps(context).map { it.packageName }

        val preApprovedPackages = listOf(
            "com.android.vending",
            "com.google.android.youtube.kids",
            "com.netflix.ninja"
        ).filter { allLaunchablePackages.contains(it) }

        sharedPreferences.edit()
            .putStringSet("approved_apps", preApprovedPackages.toSet())
            .putBoolean("apps_preloaded", true)
            .apply()
    }
}

fun getAllInstalledApps(context: Context): List<AppInfo> {
    val pm = context.packageManager

    val leanbackIntent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
    }

    val apps = pm.queryIntentActivities(leanbackIntent, 0)

    return apps.map {
        AppInfo(
            name = it.loadLabel(pm).toString(),
            packageName = it.activityInfo.packageName,
            icon = it.loadIcon(pm)
        )
    }
}




