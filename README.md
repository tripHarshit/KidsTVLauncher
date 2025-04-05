**# KidsTVLauncher**# 👶📺 Kids Launcher for Android TV

A custom launcher designed for Android TV that provides a kid-safe interface with app restrictions, PIN protection, and pre-approved apps. Perfect for parents who want to control what their kids can access on a smart TV.

---

## 🚀 Features

- 🔐 **PIN Protection**  
  Secure access to exit or modify settings using a PIN.

- 📱 **App Whitelisting**  
  Only approved apps can be accessed by kids. Others are blocked.

- 🧒 **Preloaded Apps on First Launch**  
  Apps like YouTube Kids and Play Store are available by default.

- 🛡️ **Exit Protection**  
  Prevents kids from accessing Android settings or other launchers.

- 🖼️ **Custom UI for Android TV**  
  Designed using Jetpack Compose to be clean and intuitive.

---

## 🧩 Tech Stack

- **Kotlin**  
- **Jetpack Compose**  
- **Android TV APIs**  
- **SharedPreferences** for persistent storage  
- **UsageStatsManager** for background activity monitoring  
- **AccessibilityService** for blocking settings access

---

### 🔹 Project Structure

- `MainActivity.kt`: Entry point of the app, shows either the PIN setup screen or the Kids Launcher home based on saved PIN.
- `PinSetupScreen.kt`: UI for setting up a new PIN on first launch.
- `PinScreenActivity.kt`: Auth screen to verify PIN before allowing exit.
- `KidsLauncherScreen.kt`: The custom home screen showing approved apps.
- `AppManager.kt`: Handles loading, filtering, and preloading approved apps.
- `PinManager.kt`: Stores and validates the user’s PIN securely using SharedPreferences.
- `BlockSettingsService.kt`: Accessibility service to block access to system settings (optional).

---

## 📦 Setup Instructions

1. Clone the repo:
   ```bash
   git clone https://github.com/tripHarshit/KidsTVLauncher.git
2. Open in Android Studio Arctic Fox or above
3. Connect an Android TV device or emulator
4. Build & Run

---

## **🛠️ Permissions Used**

| **Permission** | **Purpose** |
|----------------|-------------|
| `INTERNET` | For future enhancements like syncing or remote config |
| `MANAGE_OVERLAY_PERMISSION` | To block overlays if needed |
| `FOREGROUND_SERVICE` | To run services in background |
| `READ_EXTERNAL_STORAGE` | Reserved for future media access features |
| `BIND_ACCESSIBILITY_SERVICE` | To block access to settings |

> ⚠️ Note: Some permissions like `QUERY_ALL_PACKAGES` may be restricted in SDK 30+. Use `queries` tag in manifest for targeting specific packages.

