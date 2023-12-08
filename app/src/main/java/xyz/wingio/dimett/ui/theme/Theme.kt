package xyz.wingio.dimett.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Wrapper around [MaterialTheme] that uses the correct color scheme automatically and sets up edge-to-edge
 *
 * @param darkTheme Whether or not to use the dark theme
 * @param dynamicColor Whether or not to use dynamic theming (Only supported on Android 12+)
 */
@Composable
fun DimettTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Make sure that even if the dynamicColor option is true it'll still require Android 12+
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.apply {
            setSystemBarsColor(
                color = Color.Transparent, // Let components be visible under the nav and status bars
                darkIcons = !darkTheme,
                isNavigationBarContrastEnforced = true
            )
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}