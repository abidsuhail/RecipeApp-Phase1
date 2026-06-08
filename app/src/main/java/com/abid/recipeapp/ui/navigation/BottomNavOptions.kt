package com.abid.recipeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.abid.recipeapp.util.Constants.FAVOURITE
import com.abid.recipeapp.util.Constants.HOME
import com.abid.recipeapp.util.Constants.SEARCH
import com.abid.recipeapp.util.Constants.SETTINGS


sealed class BottomNavOptions(val route: String, val icon: ImageVector, val label: String) {
    data object Home :
        BottomNavOptions(NavHelper.Screen.HomeRecipeListScreen.route, Icons.Default.Home, HOME)

    data object Search :
        BottomNavOptions(NavHelper.Screen.Search.route, Icons.Default.Search, SEARCH)

    data object Favourite :
        BottomNavOptions(NavHelper.Screen.Favourite.route, Icons.Default.Star, FAVOURITE)

    data object Profile :
        BottomNavOptions(NavHelper.Screen.Settings.route, Icons.Default.Settings, SETTINGS)
}