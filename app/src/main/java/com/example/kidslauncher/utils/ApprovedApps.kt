package com.example.kidslauncher.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.edit
import com.example.kidslauncher.models.AppInfo

fun getApprovedApps(context: Context): List<AppInfo> {
    val packageManager = context.packageManager
    val sharedPreferences = context.getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
    val approvedPackages = sharedPreferences.getStringSet("approved_apps", emptySet()) ?: emptySet()

    return approvedPackages.mapNotNull { packageName ->
        try {
            val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            AppInfo(
                name = packageManager.getApplicationLabel(appInfo).toString(),
                packageName = appInfo.packageName,
                icon = packageManager.getApplicationIcon(packageName)
            )
        } catch (e: PackageManager.NameNotFoundException) {
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

fun isAppApproved(context: Context, packageName: String): Boolean {
    val sharedPreferences = context.getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
    val approvedPackages = sharedPreferences.getStringSet("approved_apps", emptySet()) ?: emptySet()
    return approvedPackages.contains(packageName)
}


