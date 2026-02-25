package com.abid.recipeapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBarContainer(
    currentRoute: String?,
    onNavItemClicked: (String?) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(currentRoute) { route ->
                onNavItemClicked(route)
            }
        }) { innerPadding ->
        content(innerPadding)
    }
}