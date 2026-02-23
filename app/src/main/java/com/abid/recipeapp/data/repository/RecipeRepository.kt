package com.abid.recipeapp.data.repository

import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.MockData
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
    private val recipes = MockData.mockRecipes

    suspend fun getAllRecipes(): List<RecipeModel> {
        return recipes
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        if (id == null) return null
        return recipes.find { it.id == id }
    }
}