package com.abid.recipeapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abid.recipeapp.ui.navigation.NavHelper.getScreenByRoute
import com.abid.recipeapp.ui.navigation.NavHelper.goToCreateHomeScreen
import com.abid.recipeapp.ui.navigation.NavHelper.goToCreateRecipeScreen
import com.abid.recipeapp.ui.navigation.NavHelper.goToRecipeDetailsScreen
import com.abid.recipeapp.ui.screens.CreateRecipeScreen
import com.abid.recipeapp.ui.screens.FavRecipeScreen
import com.abid.recipeapp.ui.screens.RecipeDetailsScreen
import com.abid.recipeapp.ui.screens.RecipeListScreen
import com.abid.recipeapp.ui.screens.SearchRecipeScreen
import com.abid.recipeapp.ui.screens.SettingsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val screen = getScreenByRoute(currentRoute)
    val isShowBottomBar = screen.showBottomBar
    if (isShowBottomBar) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavBar(currentRoute) { route ->
                    navController.navigate(route!!)
                }
            }) { innerPadding ->
            AppNavGraph(navController, innerPadding)
        }
    } else {
        AppNavGraph(navController)
    }

}

@Composable
private fun AppNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    NavHost(
        navController = navController,
        startDestination = NavHelper.Screen.HomeRecipeListScreen.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = NavHelper.Screen.HomeRecipeListScreen.route) {
            RecipeListScreen(
                onClickRecipeItem = { recipe ->
                    navController.goToRecipeDetailsScreen(recipe.id)
                }, onClickAddRecipe = {
                    navController.goToCreateRecipeScreen()
                }
            )
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
            SearchRecipeScreen()
        }
        composable(
            route = NavHelper.Screen.Favourite.route,
        ) { backStackEntry ->
            FavRecipeScreen()
        }
        composable(
            route = NavHelper.Screen.Settings.route,
        ) { backStackEntry ->
            SettingsScreen()
        }
        composable(
            route = NavHelper.Screen.CreateRecipeScreen.route,
        ) { backStackEntry ->
            CreateRecipeScreen(
                onClickBackBtn = { navController.popBackStack() },
                handleGotoHomeScreen = {
                    navController.goToCreateHomeScreen()
                })
        }
    }
}

