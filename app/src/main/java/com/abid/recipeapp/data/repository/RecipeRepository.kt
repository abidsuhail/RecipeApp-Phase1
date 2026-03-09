package com.abid.recipeapp.data.repository

import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_CATEGORIES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_CUISINES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_DIFFICULTY_LEVELS
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_INGREDIENTS_INST_TAB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    suspend fun getAllRecipes(): List<RecipeModel> {
        return withContext(Dispatchers.IO) {
            val recipes = db.collection(RECIPES).get().await()
            recipes.toObjects(RecipeModel::class.java)
        }
    }

    suspend fun getIngredientsTabsItems(): List<IngredientsTabModel> {
        return withContext(Dispatchers.IO) {
            val ingredientsAndInstTabItems =
                db.collection(RECIPE_INGREDIENTS_INST_TAB).get().await()
            ingredientsAndInstTabItems.toObjects(IngredientsTabModel::class.java)
        }
    }

    suspend fun getRecipeCategories(): List<RecipeCategoriesDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCategories = db.collection(RECIPE_CATEGORIES).get().await()
            recipeCategories.toObjects(RecipeCategoriesDataModel::class.java)
        }
    }

    suspend fun getCuisines(): List<RecipeCuisinesDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCuisines = db.collection(RECIPE_CUISINES).get().await()
            recipeCuisines.toObjects(RecipeCuisinesDataModel::class.java)
        }
    }

    suspend fun getRecipeDifficulties(): List<RecipeDiffLvlDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCuisines = db.collection(RECIPE_DIFFICULTY_LEVELS).get().await()
            recipeCuisines.toObjects(RecipeDiffLvlDataModel::class.java)
        }
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        if (id == null) return null
        return withContext(Dispatchers.IO) {
            val recipe = db.collection(RECIPES).document(id).get().await()
            recipe.toObject(RecipeModel::class.java)
        }
    }
}