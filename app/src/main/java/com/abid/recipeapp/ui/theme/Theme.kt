package com.abid.recipeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abid.recipeapp.ui.navigation.AppNavHost
import com.abid.recipeapp.viewmodel.SyncViewModel
import com.abid.recipeapp.viewmodel.ThemeViewModel

val LocalIsDarkTheme = compositionLocalOf { false }
val LocalAppCustomColors = compositionLocalOf { LightAppColors }

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Orange40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

enum class AppThemeMode {
    LIGHT, DARK, SYSTEM
}

fun String?.toAppTheme(): AppThemeMode {
    return when (this) {
        AppThemeMode.DARK.name -> {
            AppThemeMode.DARK
        }

        AppThemeMode.LIGHT.name -> {
            AppThemeMode.LIGHT
        }

        AppThemeMode.SYSTEM.name -> {
            AppThemeMode.SYSTEM
        }

        else -> {
            AppThemeMode.SYSTEM
        }
    }
}


@Composable
fun RecipeMainApp(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel = hiltViewModel()
) {
    val appThemeMode by themeViewModel.state.collectAsStateWithLifecycle()
    val isInternetConnected by syncViewModel.isConnected.collectAsStateWithLifecycle()

    val isDark = when (appThemeMode) {
        AppThemeMode.DARK -> true
        AppThemeMode.LIGHT -> false
        AppThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme
    val customColors = if (isDark) DarkAppColors else LightAppColors
    CompositionLocalProvider(LocalAppCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = {

                AppNavHost(isInternetConnected) {
                    themeViewModel.setTheme(it)
                }
            }
        )
    }
}