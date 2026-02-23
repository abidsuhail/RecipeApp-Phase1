package com.abid.recipeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abid.recipeapp.ui.screens.RecipeDetailsScreen
import com.abid.recipeapp.ui.screens.RecipeListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavHelper.Screen.RecipeList.route
    ) {
        composable(route = NavHelper.Screen.RecipeList.route) { RecipeListScreen(navController) }
        composable(
            route = NavHelper.Screen.RecipeDetails.route,
            arguments = listOf(navArgument(NavHelper.NAV_ARGS_RECIPE_ID_KEY) {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString(NavHelper.NAV_ARGS_RECIPE_ID_KEY)
            RecipeDetailsScreen(recipeId, navController)
        }
    }
}