package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.ui.theme.AppThemeMode
import com.abid.recipeapp.viewmodel.ThemeViewModel


@Composable
fun SettingsScreen(themeViewModel: ThemeViewModel = hiltViewModel()) {

    Box(Modifier.padding(10.dp)) {
        Column {
            Text("Settings", fontSize = 35.sp)
            Card(
                modifier = Modifier.padding(vertical = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
            ) {
                Box(modifier = Modifier.padding(10.dp)) {
                    Column {
                        Text("Theme", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                        Text("Choose your preferred theme", fontSize = 12.sp)
                        Row {
                            ThemeModeGridItem(AppThemeMode.LIGHT) {
                                themeViewModel.setTheme(it)
                            }
                            ThemeModeGridItem(AppThemeMode.DARK) {
                                themeViewModel.setTheme(it)
                            }
                            ThemeModeGridItem(AppThemeMode.SYSTEM) {
                                themeViewModel.setTheme(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.ThemeModeGridItem(
    themeMode: AppThemeMode,
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
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
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