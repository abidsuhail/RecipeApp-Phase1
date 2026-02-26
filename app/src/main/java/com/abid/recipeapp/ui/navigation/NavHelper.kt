package com.abid.recipeapp.ui.navigation

import androidx.navigation.NavController

object NavHelper {
    const val NAV_ARGS_RECIPE_ID_KEY = "recipe_id"

    sealed class Screen(val route: String, val showBottomBar: Boolean) {
        data object HomeRecipeListScreen : Screen("home", true)
        data object Search : Screen("search", true) {
            fun createRoute() = "search"
        }

        data object Favourite : Screen("favourite", true)
        data object Settings : Screen("settings", true)
        data object RecipeDetails : Screen("recipe_details/{${NAV_ARGS_RECIPE_ID_KEY}}", false) {
            fun createRoute(recipeId: String) = "recipe_details/${recipeId}"
        }

        data object CreateRecipeScreen : Screen("create_recipe", false)

    }

    fun getScreenByRoute(route: String?): Screen {
        return when (route) {
            Screen.HomeRecipeListScreen.route, null -> {
                Screen.HomeRecipeListScreen
            }

            Screen.RecipeDetails.route -> {
                Screen.RecipeDetails
            }

            Screen.Search.route -> {
                Screen.Search
            }

            Screen.Favourite.route -> {
                Screen.Favourite
            }

            Screen.Settings.route -> {
                Screen.Settings
            }

            Screen.CreateRecipeScreen.route -> {
                Screen.CreateRecipeScreen
            }

            else -> {
                throw Exception("Invalid screen route")
            }
        }
    }

    fun NavController.goToRecipeDetailsScreen(recipeId: String) {
        navigate(Screen.RecipeDetails.createRoute(recipeId))
    }

    fun NavController.goToSearchScreen() {
        navigate(Screen.Search.createRoute())
    }

    fun NavController.goToCreateRecipeScreen() {
        navigate(Screen.CreateRecipeScreen.route)
    }
}
