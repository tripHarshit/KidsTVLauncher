package com.example.kidslauncher

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import com.example.kidslauncher.utils.getApprovedApps

class BlockSettingsService : AccessibilityService() {

    private var approvedApps: Set<String> = emptySet()

    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            approvedApps = getApprovedApps(context).map { it.packageName }.toSet()
            Log.d("BlockService", "Approved apps updated: $approvedApps")
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate() {
        super.onCreate()
        approvedApps = getApprovedApps(this).map { it.packageName }.toSet()

        val filter = IntentFilter("com.example.kidslauncher.UPDATE_APPROVED_APPS")
        registerReceiver(updateReceiver, filter)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            val packageName = it.packageName?.toString() ?: return
            Log.d("BlockService", "Detected app: $packageName")

            if (packageName !in approvedApps) {
                Log.d("BlockService", "Blocking app: $packageName")
                performGlobalAction(GLOBAL_ACTION_HOME)
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(updateReceiver)
        super.onDestroy()
    }

    override fun onInterrupt() {}
}

