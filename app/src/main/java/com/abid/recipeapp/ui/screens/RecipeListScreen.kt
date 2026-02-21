package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abid.recipeapp.ui.components.RecipeCard
import com.abid.recipeapp.util.BottomNavOptions
import com.abid.recipeapp.util.MockData
import com.abid.recipeapp.util.NavHelper.goToRecipeDetailsScreen

@Composable
fun RecipeListScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                RecipeListHeader()
                RecipeList(navController)
            }
        }
    }
}

@Composable
private fun RecipeListHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        Text("Discover Recipes", fontSize = 30.sp)
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Add button",
            modifier = Modifier.size(50.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun RecipeList(navController: NavHostController) {
    LazyColumn {
        items(items = MockData.mockRecipes) { recipe ->
            Box(modifier = Modifier.clickable {
                navController.goToRecipeDetailsScreen(recipe)
            }) {
                RecipeCard(recipe)
            }
        }
    }
}

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