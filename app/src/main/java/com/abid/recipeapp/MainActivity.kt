package com.abid.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.abid.recipeapp.ui.navigation.AppNavHost
import com.abid.recipeapp.ui.theme.RecipeAppTheme
import com.abid.recipeapp.util.RecipeDiffLvl
import dagger.hilt.android.AndroidEntryPoint

data class IngredientsTabModel(
    val id: String = "",
    val label: String = ""
)

data class RecipeCategoriesDataModel(
    val id: String = "",
    val label: String = ""
)

data class CuisinesDataModel(
    val id: String = "",
    val label: String = ""
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val ingredientsAndInstTabItems =
        listOf(IngredientsTabModel("1", "Ingredients"), IngredientsTabModel("2", "Instructions"))
    private val newRecipeCategories =
        listOf("Breakfast", "Lunch", "Dinner", "Dessert", "Salad", "Snack")
    private val newRecipeCuisine = listOf(
        "American",
        "Italian",
        "Mexican",
        "Indian",
        "Chinese",
        "Thai",
        "Greek",
        "French",
        "Japanese"
    )
    private val newRecipeDifficultyLvl =
        listOf(RecipeDiffLvl.EASY, RecipeDiffLvl.MEDIUM, RecipeDiffLvl.HARD)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                AppNavHost()
            }
        }
    }
}
