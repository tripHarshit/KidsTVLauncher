package com.example.kidslauncher.utils

import android.content.Context
import android.content.pm.PackageManager
import com.example.kidslauncher.models.AppInfo
import androidx.core.content.edit

fun getApprovedApps(context: Context): List<AppInfo> {
    val packageManager = context.packageManager
    val sharedPreferences = context.getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
    val approvedPackages = sharedPreferences.getStringSet("approved_apps", setOf(
        "com.google.android.youtube.kids",
        "com.netflix.mediaclient",
        "com.android.tv.settings",    // TV Settings
        "com.android.vending",        // Google Play Store
        "com.google.android.gms",     // Google Play Services
        "com.google.android.tts"
    )) ?: emptySet()

    return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        .filter { it.packageName in approvedPackages }
        .map {
            AppInfo(
                name = packageManager.getApplicationLabel(it).toString(),
                packageName = it.packageName,
                icon = packageManager.getApplicationIcon(it)
            )
        }
}


fun saveApprovedApps(context: Context, approvedApps: List<String>) {
    val sharedPreferences = context.getSharedPreferences("KidsLauncherPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit() {
        putStringSet("approved_apps", approvedApps.toSet())
    }
}


