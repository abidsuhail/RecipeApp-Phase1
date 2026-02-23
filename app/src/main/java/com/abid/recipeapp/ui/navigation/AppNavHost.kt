package com.abid.recipeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.screens.RecipeDetailsScreen
import com.abid.recipeapp.ui.screens.RecipeListScreen
import com.abid.recipeapp.util.NavHelper

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
            arguments = listOf(navArgument("recipe") {
                type = NavType.ParcelableType(RecipeModel::class.java)
                nullable = true
            })
        ) { entry ->
            val recipe = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<RecipeModel>("recipe")

            recipe?.let {
                RecipeDetailsScreen(navController, recipe)
            }
        }
    }
}