package com.abid.recipeapp.util

import androidx.navigation.NavController
import com.abid.recipeapp.data.model.RecipeModel

object NavHelper {
    sealed class Screen(val route: String) {
        data object Home : Screen("home")
        data object Search : Screen("search")
        data object Favourite : Screen("favourite")
        data object Profile : Screen("profile")
        data object RecipeList : Screen("recipe_list")
        data object RecipeDetails : Screen("recipe_details") {
            fun createRoute() = "recipe_details"
        }
    }

    fun NavController.goToRecipeDetailsScreen(recipe: RecipeModel) {
        currentBackStackEntry
            ?.savedStateHandle
            ?.set("recipe", recipe)
        navigate(Screen.RecipeDetails.createRoute())
    }
}
