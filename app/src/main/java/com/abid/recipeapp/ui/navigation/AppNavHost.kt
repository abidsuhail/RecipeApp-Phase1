package com.abid.recipeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abid.recipeapp.ui.navigation.NavHelper.goToRecipeDetailsScreen
import com.abid.recipeapp.ui.screens.RecipeDetailsScreen
import com.abid.recipeapp.ui.screens.RecipeListScreen
import com.abid.recipeapp.ui.screens.SearchRecipeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
 
    NavHost(
        navController = navController,
        startDestination = NavHelper.Screen.Home.route
    ) {
        composable(route = NavHelper.Screen.Home.route) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavBarContainer(
                currentRoute = currentRoute,
                onNavItemClicked = { navController.navigate(it!!) }) { paddingValues ->
                RecipeListScreen(paddingValues) { recipe ->
                    navController.goToRecipeDetailsScreen(recipe.id)
                }
            }
        }
        composable(
            route = NavHelper.Screen.RecipeDetails.route,
            arguments = listOf(navArgument(NavHelper.NAV_ARGS_RECIPE_ID_KEY) {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString(NavHelper.NAV_ARGS_RECIPE_ID_KEY)
            RecipeDetailsScreen(recipeId, {
                navController.popBackStack()
            })
        }
        composable(
            route = NavHelper.Screen.Search.route,
        ) { backStackEntry ->
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavBarContainer(
                currentRoute = currentRoute,
                onNavItemClicked = { navController.navigate(it!!) }) { paddingValues ->
                SearchRecipeScreen()
            }
        }
    }
}

