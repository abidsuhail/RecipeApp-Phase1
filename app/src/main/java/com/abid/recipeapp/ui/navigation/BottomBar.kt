package com.abid.recipeapp.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomBar(currentRoute: String?) {
    val items = listOf<BottomNavOptions>(
        BottomNavOptions.Home,
        BottomNavOptions.Search,
        BottomNavOptions.Favourite,
        BottomNavOptions.Profile
    )

    NavigationBar(containerColor = Color.White) {

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