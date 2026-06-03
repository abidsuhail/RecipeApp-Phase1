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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.BuildConfig
import com.abid.recipeapp.ui.state.SettingsUiState
import com.abid.recipeapp.ui.theme.AppThemeMode
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.ui.theme.SettingsTheme
import com.abid.recipeapp.util.Constants
import com.abid.recipeapp.viewmodel.SettingsViewModel


@Composable
fun SettingsScreen(
    vm: SettingsViewModel = hiltViewModel(),
    onThemeSelected: (AppThemeMode) -> Unit
) {
    val state by vm.state.observeAsState(initial = SettingsUiState(AppThemeMode.SYSTEM))

    Box(Modifier.padding(10.dp)) {
        Column {
            Text("Settings", fontSize = 35.sp)
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
    Box(modifier = Modifier.padding(10.dp)) {
        Column {
            Text("Theme", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text("Choose your preferred theme", fontSize = 12.sp)
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
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text("About", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Version")
            Text(BuildConfig.VERSION_NAME)
        }
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total Recipes")
            Text("8")
        }
    }
}

@Composable
private fun AppInfoCardItem() {
    Box(modifier = Modifier.padding(10.dp)) {
        Column {
            Text("App Info", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(
                Constants.RECIPE_APP_INFO,
                fontSize = 12.sp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
private fun GrayCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = SettingsTheme.cardColor)
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
            .height(100.dp)
            .padding(8.dp)
            .weight(1.0f)
            .clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onThemeSelected(themeMode)
            },
        border = BorderStroke(2.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = SettingsTheme.themeModeGrid),
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