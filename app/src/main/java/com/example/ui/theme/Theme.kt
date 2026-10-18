package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = YouTubeRed,
    onPrimary = TextPrimary,
    primaryContainer = YouTubeRedDark,
    onPrimaryContainer = TextPrimary,
    secondary = AccentCyan,
    onSecondary = SurfaceDark0,
    secondaryContainer = SurfaceDark2,
    onSecondaryContainer = AccentCyan,
    tertiary = AccentAmber,
    onTertiary = SurfaceDark0,
    background = SurfaceDark0,
    onBackground = TextPrimary,
    surface = SurfaceDark1,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceDark2,
    onSurfaceVariant = TextSecondary,
    outline = SurfaceDarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = YouTubeRed,
    onPrimary = TextPrimary,
    primaryContainer = YouTubeRedLight,
    onPrimaryContainer = TextPrimary,
    secondary = AccentCyan,
    onSecondary = SurfaceDark0,
    secondaryContainer = SurfaceDark2,
    onSecondaryContainer = AccentCyan,
    tertiary = AccentAmber,
    onTertiary = SurfaceDark0,
    background = SurfaceDark0, // Using immersive creator studio dark theme
    onBackground = TextPrimary,
    surface = SurfaceDark1,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceDark2,
    onSurfaceVariant = TextSecondary,
    outline = SurfaceDarkBorder
)

@Composable
fun TubeSeoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = SurfaceDark0.toArgb()
                window.navigationBarColor = SurfaceDark0.toArgb()
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
