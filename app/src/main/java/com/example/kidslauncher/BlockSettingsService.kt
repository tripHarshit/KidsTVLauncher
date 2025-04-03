package com.example.kidslauncher


import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Intent

class BlockSettingsService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.packageName == "com.android.settings") {
            performGlobalAction(GLOBAL_ACTION_HOME) // Send user to home screen
        }
    }

    override fun onInterrupt() {}
}
