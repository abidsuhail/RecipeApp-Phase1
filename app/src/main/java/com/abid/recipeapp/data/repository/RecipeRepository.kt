package com.abid.recipeapp.data.repository

import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.MockData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
    private val recipes = MockData.mockRecipes

    suspend fun getAllRecipes(): List<RecipeModel> {
        return withContext(Dispatchers.IO) {  // ← switch to IO here
            delay(1000)
            recipes
        }
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        if (id == null) return null
        return withContext(Dispatchers.IO) {  // ← switch to IO here
            delay(1000)
            recipes.find { it.id == id }
        }
    }
}