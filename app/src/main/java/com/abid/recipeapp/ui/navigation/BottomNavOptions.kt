package com.abid.recipeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavOptions(val route: String, val icon: ImageVector, val label: String) {
    data object Home :
        BottomNavOptions(NavHelper.Screen.HomeRecipeListScreen.route, Icons.Default.Home, "Home")

    data object Search :
        BottomNavOptions(NavHelper.Screen.Search.route, Icons.Default.Search, "Search")

    data object Favourite :
        BottomNavOptions(NavHelper.Screen.Favourite.route, Icons.Default.Star, "Favourite")

    data object Profile :
        BottomNavOptions(NavHelper.Screen.Settings.route, Icons.Default.Settings, "Settings")
}