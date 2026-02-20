package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun RecipeDetailsScreen(navController: NavHostController, recipeId: String) {
    Text("Recipe details list : ${recipeId}", modifier = Modifier.padding(top = 50.dp))
}