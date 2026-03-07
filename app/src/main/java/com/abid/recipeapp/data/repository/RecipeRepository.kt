package com.abid.recipeapp.data.repository

import com.abid.recipeapp.IngredientsTabModel
import com.abid.recipeapp.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.RecipeDiffLvl
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
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
    private val db = FirebaseFirestore.getInstance()

    suspend fun getAllRecipes(): List<RecipeModel> {
        return withContext(Dispatchers.IO) {
            val recipes = db.collection("recipes").get().await()
            recipes.toObjects(RecipeModel::class.java)
        }
    }

    suspend fun getIngredientsTabsItems(): List<IngredientsTabModel> {
        return withContext(Dispatchers.IO) {
            val ingredientsAndInstTabItems =
                db.collection("ingredients_instructions_tabs").get().await()
            ingredientsAndInstTabItems.toObjects(IngredientsTabModel::class.java)
        }
    }

    suspend fun getRecipeCategories(): List<RecipeCategoriesDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCategories = db.collection("recipe_categories").get().await()
            recipeCategories.toObjects(RecipeCategoriesDataModel::class.java)
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
            null
        }
    }
}