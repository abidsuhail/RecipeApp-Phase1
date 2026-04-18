package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class AppThemeMode {
    LIGHT, DARK, SYSTEM
}

@Composable
fun SettingsScreen() {
    Column {
        Text("Settings", fontSize = 35.sp)
        Card(
            modifier = Modifier.padding(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                Column {
                    Text("Theme")
                    Text("Choose your preferred theme")
                    Row {
                        ThemeModeGridItem(AppThemeMode.LIGHT)
                        ThemeModeGridItem(AppThemeMode.DARK)
                        ThemeModeGridItem(AppThemeMode.SYSTEM)
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.ThemeModeGridItem(themeMode: AppThemeMode) {
    val icon = when (themeMode) {
        AppThemeMode.LIGHT -> {
            Icons.Filled.AddCircle
        }

        AppThemeMode.DARK -> {
            Icons.Filled.AddCircle
        }

        else -> {
            Icons.Filled.AddCircle
        }
    }
    Card(
        Modifier
            .height(100.dp)
            .padding(8.dp)
            .weight(1.0f),
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