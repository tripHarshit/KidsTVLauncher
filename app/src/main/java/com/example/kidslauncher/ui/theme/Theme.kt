package com.example.kidslauncher.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Shapes
import androidx.tv.material3.Typography
import androidx.tv.material3.darkColorScheme
import androidx.tv.material3.lightColorScheme
import com.example.kidslauncher.ui.theme.Typography

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun KidsLauncherTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = Color(0xFFFFA000),
        background = Color(0xFFF5F5F5),
        onPrimary = Color.White
    )

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
