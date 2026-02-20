package com.abid.recipeapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.abid.recipeapp.util.MockData
import com.abid.recipeapp.util.NavHelper.goToRecipeDetailsScreen

@Composable
fun RecipeList(navController: NavHostController) {
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