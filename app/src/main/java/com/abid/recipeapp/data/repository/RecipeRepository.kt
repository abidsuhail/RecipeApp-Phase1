package com.abid.recipeapp.data.repository

import android.net.Uri
import com.abid.recipeapp.data.local.RecipeLocalDataSource
import com.abid.recipeapp.data.model.CreateRecipeFormStateModel
import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.data.remote.RecipeRemoteDataSource
import com.abid.recipeapp.util.network.NetworkMonitor
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val recipeLocalDataSource: RecipeLocalDataSource,
    private val recipeRemoteDataSource: RecipeRemoteDataSource
) {
    suspend fun getAndSaveAllRecipes(): List<RecipeModel> = withContext(Dispatchers.IO) {
        if (networkMonitor.isConnected()) {
            val allRecipes = recipeRemoteDataSource.getAllRecipes()
            recipeLocalDataSource.saveRecipeListToDb(allRecipes)   // always saves — no condition needed
            allRecipes
        } else {
            recipeLocalDataSource.getLocalAllRecipesList()
        }
    }

    suspend fun getRecipesByFilters(
        categoryName: String? = null,
        cuisineName: String? = null,
        diffLvl: String? = null,
        title: String? = null
    ): List<RecipeModel> {
        return withContext(Dispatchers.IO) {
            if (networkMonitor.isConnected()) {
                recipeRemoteDataSource.getRecipesByFilters(
                    categoryName,
                    cuisineName,
                    diffLvl,
                    title
                )
            } else {
                val localRecipeList = recipeLocalDataSource.getLocalAllRecipesList()
                localRecipeList
            }
        }
    }

    suspend fun getIngredientsTabsItems(): List<IngredientsTabModel> {
        return recipeRemoteDataSource.getIngredientsTabsItems()
    }

    suspend fun getRecipeCategories(): List<RecipeCategoriesDataModel> {
        return recipeRemoteDataSource.getRecipeCategories()
    }

    suspend fun getCuisines(): List<RecipeCuisinesDataModel> {
        return recipeRemoteDataSource.getCuisines()
    }

    suspend fun getRecipeDifficulties(): List<RecipeDiffLvlDataModel> {
        return recipeRemoteDataSource.getRecipeDifficulties()
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        return recipeRemoteDataSource.getRecipeById(id)
    }

    suspend fun saveRecipe(recipeModel: CreateRecipeFormStateModel): Task<Void> {
        return recipeRemoteDataSource.saveRecipe(recipeModel)
    }

    suspend fun toggleFav(recipeId: String) {
        recipeRemoteDataSource.toggleFav(recipeId)
    }

    suspend fun getAllFavRecipesList(): List<RecipeModel> {
        return recipeRemoteDataSource.getAllFavRecipesList()
    }

    suspend fun isFav(recipeId: String): Boolean {
        return recipeRemoteDataSource.isFav(recipeId)
    }

    suspend fun uploadImage(imageUri: Uri): String {
        return recipeRemoteDataSource.uploadImage(imageUri)
    }
}