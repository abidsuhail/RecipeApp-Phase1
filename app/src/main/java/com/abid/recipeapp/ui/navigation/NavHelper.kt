package com.abid.recipeapp.ui.navigation

import androidx.navigation.NavController

object NavHelper {
    const val NAV_ARGS_RECIPE_ID_KEY = "recipe_id"

    sealed class Screen(val route: String) {
        data object Home : Screen("home")
        data object Search : Screen("search")
        data object Favourite : Screen("favourite")
        data object Profile : Screen("profile")
        data object RecipeList : Screen("recipe_list")
        data object RecipeDetails : Screen("recipe_details/{${NAV_ARGS_RECIPE_ID_KEY}}") {
            fun createRoute(recipeId: String) = "recipe_details/${recipeId}"
        }
    }

    fun NavController.goToRecipeDetailsScreen(recipeId: String) {
        navigate(Screen.RecipeDetails.createRoute(recipeId))
    }
}
