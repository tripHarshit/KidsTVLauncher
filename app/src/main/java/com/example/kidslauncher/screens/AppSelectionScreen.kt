package com.example.kidslauncher.screens

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.kidslauncher.utils.addApprovedApp

@Composable
fun AppSelectionScreen(onAppSelected: (String) -> Unit) {
    val context = LocalContext.current
    val packageManager = context.packageManager

    val installedApps = remember {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .map {
                AppInfo(
                    name = packageManager.getApplicationLabel(it).toString(),
                    packageName = it.packageName,
                    icon = packageManager.getApplicationIcon(it)
                )
            }
    }

    installedApps.forEach { app ->
        Log.d("AppSelection", "Installed App: ${app.packageName}")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(installedApps) { app ->
                AppItem(app, context, onClick = {
                    addApprovedApp(context, app.packageName)
                    onAppSelected(app.packageName)
                    Log.d("AppSelection", "Added App: ${app.packageName}")
                })
            }
        }
    }
}

@Composable
fun AppItem(app: AppInfo, context: Context, onClick: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .padding(12.dp)
            .background(Color(0xFFFFF176), shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
            .clickable { onClick?.invoke() }
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
