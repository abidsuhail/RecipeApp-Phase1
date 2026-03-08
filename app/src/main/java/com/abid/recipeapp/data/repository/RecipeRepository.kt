package com.abid.recipeapp.data.repository

import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor() {

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

    suspend fun getCuisines(): List<RecipeCuisinesDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCuisines = db.collection("recipe_cuisines").get().await()
            recipeCuisines.toObjects(RecipeCuisinesDataModel::class.java)
        }
    }

    suspend fun getRecipeDifficulties(): List<RecipeDiffLvlDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCuisines = db.collection("difficulty_levels").get().await()
            recipeCuisines.toObjects(RecipeDiffLvlDataModel::class.java)
        }
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        if (id == null) return null
        return withContext(Dispatchers.IO) {
            val recipe = db.collection("recipes").document(id).get().await()
            recipe.toObject(RecipeModel::class.java)
        }
    }
}