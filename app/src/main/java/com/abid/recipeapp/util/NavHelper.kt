package com.abid.recipeapp.util

import androidx.navigation.NavController

object NavHelper {
    sealed class Screen(val route: String) {
        data object RecipeList : Screen("recipe_list")
        data object RecipeDetails : Screen("recipe_details/{recipe_id}") {
            fun createRoute(recipeId: String) = "recipe_details/$recipeId"
        }
    }

    fun NavController.goToRecipeDetailsScreen(recipeId: String) {
        navigate(Screen.RecipeDetails.createRoute(recipeId))
    }
}
