package com.abid.recipeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavOptions(val route: String, val icon: ImageVector, val label: String) {
    data object Home : BottomNavOptions("home", Icons.Default.Home, "Home")
    data object Search : BottomNavOptions("search", Icons.Default.Search, "Search")
    data object Favourite : BottomNavOptions("favourite", Icons.Default.Search, "Favourite")
    data object Profile : BottomNavOptions("profile", Icons.Default.Person, "Profile")
}