package com.abid.recipeapp.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abid.recipeapp.util.BottomNavOptions

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf<BottomNavOptions>(
        BottomNavOptions.Home,
        BottomNavOptions.Search,
        BottomNavOptions.Favourite,
        BottomNavOptions.Profile
    )

    NavigationBar(containerColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {

                }
            )
        }
    }
}