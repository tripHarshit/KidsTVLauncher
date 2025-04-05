package com.example.kidslauncher.models

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

object PinManager {
    private const val PREFS_NAME = "kids_launcher_prefs"
    private const val PIN_KEY = "launcher_pin"

    fun savePin(context: Context, pin: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit { putString(PIN_KEY, pin) }
        Log.d("PinManager", "Saved PIN: $pin") // Log when PIN is saved
    }

    fun getPin(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PIN_KEY, null)
    }

    fun isPinSet(context: Context): Boolean {
        val storedPin = getPin(context)
        Log.d("PinManager", "Stored PIN: $storedPin")

        return storedPin != null
    }
}
