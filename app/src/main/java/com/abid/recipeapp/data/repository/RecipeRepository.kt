package com.abid.recipeapp.data.repository

import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.MockData
import com.abid.recipeapp.util.RecipeDiffLvl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
    private val recipes = MockData.mockRecipes
    private val ingredientsAndInstTabItems = listOf("Ingredients", "Instructions")
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

    suspend fun getAllRecipes(): List<RecipeModel> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            recipes
        }
    }

    suspend fun getIngredientsTabsItems(): List<String> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            ingredientsAndInstTabItems
        }
    }

    suspend fun getRecipeCategories(): List<String> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            newRecipeCategories
        }
    }

    suspend fun getCuisines(): List<String> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            newRecipeCuisine
        }
    }

    suspend fun getRecipeDifficulties(): List<RecipeDiffLvl> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            newRecipeDifficultyLvl
        }
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        if (id == null) return null
        return withContext(Dispatchers.IO) {
            delay(1000)
            recipes.find { it.id == id }
        }
    }
}