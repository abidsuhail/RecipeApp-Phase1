package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.SystemSecurityUpdateGood
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abid.recipeapp.BuildConfig
import com.abid.recipeapp.ui.theme.AppThemeMode
import com.abid.recipeapp.ui.theme.LocalAppCustomColors
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Constants
import com.abid.recipeapp.util.Constants.ABOUT
import com.abid.recipeapp.util.Constants.APP_INFO
import com.abid.recipeapp.util.Constants.CHOOSE_YOUR_PREFERRED_THEME
import com.abid.recipeapp.util.Constants.SETTINGS
import com.abid.recipeapp.util.Constants.THEME
import com.abid.recipeapp.util.Constants.TOTAL_RECIPES
import com.abid.recipeapp.util.Constants.VERSION
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.util.Dims.W1
import com.abid.recipeapp.viewmodel.SettingsViewModel


@Composable
fun SettingsScreen(
    vm: SettingsViewModel = hiltViewModel(),
    onThemeSelected: (AppThemeMode) -> Unit
) {
    val state by vm.state.collectAsStateWithLifecycle()

    Box(Modifier.padding(Dims.Dp10)) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(SETTINGS, fontSize = Dims.Sp35)
            GrayCard {
                ThemeOptionsCardItem(state.currentTheme) {
                    onThemeSelected(it)
                    vm.setCurrentThemeOption(it)
                }
            }
            GrayCard {
                AboutCardColumn()
            }
            GrayCard {
                AppInfoCardItem()
            }
        }
    }
}


@Composable
private fun ThemeOptionsCardItem(
    currentThemeMode: AppThemeMode,
    onThemeSelected: (AppThemeMode) -> Unit
) {
    Box(modifier = Modifier.padding(Dims.Dp10)) {
        Column {
            Text(THEME, fontSize = Dims.Sp20, fontWeight = FontWeight.SemiBold)
            Text(CHOOSE_YOUR_PREFERRED_THEME, fontSize = Dims.Sp12)
            Row {
                ThemeModeGridItem(
                    AppThemeMode.LIGHT,
                    currentThemeMode == AppThemeMode.LIGHT,
                    onThemeSelected
                )
                ThemeModeGridItem(
                    AppThemeMode.DARK,
                    currentThemeMode == AppThemeMode.DARK,
                    onThemeSelected
                )
                ThemeModeGridItem(
                    AppThemeMode.SYSTEM,
                    currentThemeMode == AppThemeMode.SYSTEM,
                    onThemeSelected
                )
            }
        }
    }
}


@Composable
private fun AboutCardColumn() {
    Column(
        modifier = Modifier
            .padding(Dims.Dp10)
            .fillMaxWidth()
    ) {
        Text(ABOUT, fontSize = Dims.Sp20, fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(VERSION)
            Text(BuildConfig.VERSION_NAME)
        }
        Box(
            modifier = Modifier
                .padding(vertical = Dims.Dp8)
                .height(Dims.Dp1)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(TOTAL_RECIPES)
            Text("8")
        }
    }
}


@Composable
private fun AppInfoCardItem() {
    Box(modifier = Modifier.padding(Dims.Dp10)) {
        Column {
            Text(APP_INFO, fontSize = Dims.Sp20, fontWeight = FontWeight.SemiBold)
            Text(
                Constants.RECIPE_APP_INFO,
                fontSize = Dims.Sp12,
                modifier = Modifier.padding(Dims.Dp5)
            )
        }
    }
}

@Composable
private fun GrayCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = Dims.Dp12),
        colors = CardDefaults.cardColors(containerColor = LocalAppCustomColors.current.settingsCardColor)
    ) {
        content()
    }
}

@Composable
private fun RowScope.ThemeModeGridItem(
    themeMode: AppThemeMode,
    isSelected: Boolean,
    onThemeSelected: (AppThemeMode) -> Unit
) {
    val icon = when (themeMode) {
        AppThemeMode.LIGHT -> {
            Icons.Filled.WbSunny
        }

        AppThemeMode.DARK -> {
            Icons.Filled.DarkMode
        }

        else -> {
            Icons.Filled.SystemSecurityUpdateGood
        }
    }
    val borderColor = if (isSelected) {
        Orange40
    } else {
        Color.Unspecified
    }
    Card(
        Modifier
            .height(Dims.Dp100)
            .padding(Dims.Dp8)
            .weight(W1)
            .clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onThemeSelected(themeMode)
            },
        border = BorderStroke(Dims.Dp2, borderColor),
        colors = CardDefaults.cardColors(containerColor = LocalAppCustomColors.current.settingsThemeModeGrid),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = themeMode.name)
            Text(themeMode.name)
        }
    }
}