package com.abid.recipeapp.data.repository

import com.abid.recipeapp.data.local.RecipeLocalDataSource
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.data.remote.RecipeRemoteDataSource
import com.abid.recipeapp.util.network.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val recipeLocalDataSource: RecipeLocalDataSource,
    private val recipeRemoteDataSource: RecipeRemoteDataSource
) {
    suspend fun getAndSaveAllRecipes(
        categoryName: String? = null,
        cuisineName: String? = null,
        diffLvl: String? = null,
        title: String? = null
    ): List<RecipeModel> {
        return withContext(Dispatchers.IO) {
            if (networkMonitor.isConnected()) {
                recipeRemoteDataSource.getRecipesByFilter(
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
}