package com.abid.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.screens.RecipeDetailsScreen
import com.abid.recipeapp.ui.screens.RecipeListScreen
import com.abid.recipeapp.ui.theme.RecipeAppTheme
import com.abid.recipeapp.util.NavHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                AppNavHost()
            }
        }
    }

    @Composable
    private fun AppNavHost() {
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
}
