package com.abid.recipeapp.data.repository

import android.net.Uri
import com.abid.recipeapp.data.local.RecipeLocalDataSource
import com.abid.recipeapp.data.model.CreateRecipeFormStateModel
import com.abid.recipeapp.data.model.FavRecipeModel
import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.data.remote.RecipeRemoteDataSource
import com.abid.recipeapp.util.datamapper.toEntity
import com.abid.recipeapp.util.exception.InvalidRecipeIdException
import com.abid.recipeapp.util.exception.NoInternetException
import com.abid.recipeapp.util.exception.RecipeNotFoundException
import com.abid.recipeapp.util.network.NetworkMonitor
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val localDataSource: RecipeLocalDataSource,
    private val remoteDataSource: RecipeRemoteDataSource
) {
    suspend fun getAndSaveAllRecipes(): List<RecipeModel> = withContext(Dispatchers.IO) {
        if (networkMonitor.isConnected()) {
            val allRecipes = remoteDataSource.getAllRecipes()
            localDataSource.saveRecipeListToDb(allRecipes)
            allRecipes
        } else {
            localDataSource.getLocalAllRecipesList()
        }
    }

    suspend fun getRecipesByFilters(
        categoryName: String? = null,
        cuisineName: String? = null,
        diffLvl: String? = null,
        title: String? = null
    ): List<RecipeModel> {
        return remoteDataSource.getRecipesByFilters(
            categoryName,
            cuisineName,
            diffLvl,
            title
        )
    }

    suspend fun getAndSaveCuisines(): List<RecipeCuisinesDataModel> {
        return if (networkMonitor.isConnected()) {
            val remoteCuisines = remoteDataSource.getCuisines()
            localDataSource.saveCuisinesListToDb(remoteCuisines)
            remoteCuisines
        } else {
            localDataSource.getLocalAllCuisinesList()
        }
    }

    suspend fun getRecipeCategories(): List<RecipeCategoriesDataModel> {
        return if (networkMonitor.isConnected()) {
            val remoteCategories = remoteDataSource.getRecipeCategories()
            localDataSource.saveCategoriesListToDb(remoteCategories)
            remoteCategories
        } else {
            localDataSource.getLocalAllCategoriesList()
        }
    }


    suspend fun getAndSaveRecipeDifficulties(): List<RecipeDiffLvlDataModel> {
        return if (networkMonitor.isConnected()) {
            val remoteDiffLvls = remoteDataSource.getRecipeDifficulties()
            localDataSource.saveDiffLvlsListToDb(remoteDiffLvls)
            remoteDiffLvls
        } else {
            localDataSource.getLocalAllDiffLvlsList()
        }
    }


    suspend fun getAndSaveIngredientsTabsItems(): List<IngredientsTabModel> {
        return if (networkMonitor.isConnected()) {
            val remoteTabs = remoteDataSource.getIngredientsTabsItems()
            localDataSource.saveIngredientsTabsListToDb(remoteTabs)
            remoteTabs
        } else {
            localDataSource.getLocalAllIngredientsTabsList()
        }
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        if (id.isNullOrBlank()) {
            throw InvalidRecipeIdException()
        }
        return if (networkMonitor.isConnected()) {
            val remoteRecipe =
                remoteDataSource.getRecipeById(id) ?: throw RecipeNotFoundException()
            remoteRecipe
        } else {
            localDataSource.getLocalRecipeById(id)
        }
    }

    suspend fun getAndSaveAllFavRecipes(): List<RecipeModel> {
        return if (networkMonitor.isConnected()) {
            val remoteFavsRecipeList = remoteDataSource.getAllFavRecipesList()
            val remoteFavRecipesIdsEntity =
                remoteFavsRecipeList.map { FavRecipeModel(recipeId = it.id).toEntity() }
            localDataSource.saveFavRecipesIdsToDb(remoteFavRecipesIdsEntity)
            remoteFavsRecipeList
        } else {
            localDataSource.getLocalAllFavRecipesList()
        }
    }

    suspend fun toggleFav(recipeId: String) {
        if (!networkMonitor.isConnected()) {
            throw NoInternetException()
        }
        remoteDataSource.toggleFav(recipeId) //updated on firestore
        val isFavOnRemote = remoteDataSource.isFav(recipeId) //checking if updated success
        localDataSource.saveCurrentFavLocally(
            recipeId,
            isFavOnRemote
        ) //saving isFavOnRemote as its updated
    }

    suspend fun isFav(recipeId: String): Boolean {
        return if (networkMonitor.isConnected()) {
            remoteDataSource.isFav(recipeId)
        } else {
            localDataSource.isFavLocally(recipeId)
        }
    }

    suspend fun saveRecipe(recipeModel: CreateRecipeFormStateModel): Task<Void> {
        return remoteDataSource.saveRecipe(recipeModel)
    }

    suspend fun uploadImage(imageUri: Uri): String {
        return remoteDataSource.uploadImage(imageUri)
    }
}