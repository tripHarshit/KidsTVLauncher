package com.example.kidslauncher.screens

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.example.kidslauncher.models.AppInfo
import com.example.kidslauncher.utils.getApprovedApps
import com.example.kidslauncher.utils.saveApprovedApps

@Composable
fun KidsLauncherScreen() {
    val context = LocalContext.current
    var showAppSelection by remember { mutableStateOf(false) }
    var installedApps by remember { mutableStateOf(getApprovedApps(context)) }
    var isRemovalMode by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFDE7))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(installedApps) { app ->
                AppItem(
                    app = app,
                    context = context,
                    isRemovalMode = isRemovalMode,
                    onRemove = {
                        // Remove app from approved list
                        val newList = installedApps.filter { it.packageName != app.packageName }
                        saveApprovedApps(context, newList.map { it.packageName })
                        installedApps = getApprovedApps(context)
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Add App Button
            Button(
                onClick = { showAppSelection = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFB74D),
                    contentColor = Color.White
                )
            ) {
                Text("Add Apps", fontSize = 16.sp)
            }

            // Remove App Button
            Button(
                onClick = { isRemovalMode = !isRemovalMode },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRemovalMode) Color(0xFFEF5350) else Color(0xFF81C784),
                    contentColor = Color.White
                )
            ) {
                Text(if (isRemovalMode) "Cancel Remove" else "Remove Apps", fontSize = 16.sp)
            }
        }

        if (showAppSelection) {
            AppSelectionScreen { selectedApp ->
                val newApprovedList = installedApps.map { it.packageName } + selectedApp
                saveApprovedApps(context, newApprovedList)
                installedApps = getApprovedApps(context)
                showAppSelection = false
            }
        }
    }
}

@Composable
fun AppItem(app: AppInfo, context: Context, isRemovalMode: Boolean, onRemove: () -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(12.dp)
            .background(
                color = Color(0xFFFFF176),
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 2.dp,
                color = if (isRemovalMode) Color.Red else Color(0xFFFB8C00),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable {
                if (isRemovalMode) {
                    onRemove()
                } else {
                    context.packageManager.getLaunchIntentForPackage(app.packageName)?.let {
                        context.startActivity(it)
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Removal mode indicator
            if (isRemovalMode) {
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(8.dp)
                        .size(24.dp)
                        .background(Color.Red, RoundedCornerShape(4.dp))
                ) {
                    Text(
                        "×",
                        color = Color.White,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            app.icon?.let { drawable ->
                Image(
                    bitmap = drawable.toBitmap().asImageBitmap(),
                    contentDescription = app.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(72.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                app.name,
                fontSize = 16.sp,
                color = Color(0xFF4A148C),
                maxLines = 1
            )
        }
    }
}


