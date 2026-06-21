package com.abid.recipeapp.ui.state

import com.abid.recipeapp.ui.theme.AppThemeMode

data class SettingsUiState(
    var currentTheme: AppThemeMode = AppThemeMode.SYSTEM,
)