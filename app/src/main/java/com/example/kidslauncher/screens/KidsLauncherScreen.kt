package com.example.kidslauncher.screens

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.tv.material3.Text
import android.util.Log
import com.example.kidslauncher.models.AppInfo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*

import androidx.tv.material3.*
import com.example.kidslauncher.utils.ShowExitDialog
import com.example.kidslauncher.utils.getApprovedApps

@Composable
fun KidsLauncherScreen() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val installedApps = remember { getApprovedApps(context) }

    BackHandler {
        showDialog = true  // Show PIN dialog when back is pressed
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(installedApps) { app ->
                AppItem(app, context)
            }
        }

        if (showDialog) {
            ShowExitDialog(showDialog) {
                showDialog = false
            }
        }
    }
}


@Composable
fun AppItem(app: AppInfo, context: Context) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .size(120.dp)
            .padding(8.dp)
            .background(if (isFocused) Color(0xFFFFA000) else Color(0xFFE0E0E0))
            .focusRequester(focusRequester)
            .focusTarget()
            .onFocusChanged { isFocused = it.isFocused }
            .clickable {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(app.packageName)
                launchIntent?.let { context.startActivity(it) }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            app.icon?.let { drawable ->
                Image(
                    bitmap = drawable.toBitmap().asImageBitmap(),
                    contentDescription = app.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(app.name, fontSize = 14.sp, color = Color.Black)
        }
    }
}


