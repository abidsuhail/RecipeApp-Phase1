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
import com.abid.recipeapp.util.exception.InvalidRecipeIdException
import com.abid.recipeapp.util.network.NetworkMonitor
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val localDataSource: RecipeLocalDataSource,
    private val remoteDataSource: RecipeRemoteDataSource
) {
    fun getAllRecipes(): Flow<List<RecipeModel>> {
        return localDataSource.getLocalAllRecipesList()
    }

    suspend fun refreshAllRecipes() {
        trySyncToRemote {
            val remote = remoteDataSource.getAllRecipes()
            localDataSource.saveRecipeListToDb(remote)
        }
    }

    fun getRecipeCategories(): Flow<List<RecipeCategoriesDataModel>> {
        return localDataSource.getLocalAllCategoriesList()
    }


    suspend fun refreshCategories() {
        trySyncToRemote {
            val remoteCategories = remoteDataSource.getRecipeCategories()
            localDataSource.saveCategoriesListToDb(remoteCategories)
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

    fun getAllCuisines(): Flow<List<RecipeCuisinesDataModel>> {
        return localDataSource.getLocalAllCuisinesList()
    }

    fun getAllRecipeDifficulties(): Flow<List<RecipeDiffLvlDataModel>> {
        return localDataSource.getLocalAllDiffLvlsList()
    }

    fun getIngredientsTabsItems(): Flow<List<IngredientsTabModel>> {
        return localDataSource.getLocalAllIngredientsTabsList()
    }

    suspend fun refreshAllCuisines() {
        trySyncToRemote {
            val remoteCuisines = remoteDataSource.getCuisines()
            localDataSource.saveCuisinesListToDb(remoteCuisines)
        }
    }


    suspend fun refreshRecipeDifficulties() {
        trySyncToRemote {
            val remoteDiffLvls = remoteDataSource.getRecipeDifficulties()
            localDataSource.saveDiffLvlsListToDb(remoteDiffLvls)
        }
    }


    suspend fun refreshIngredientsTabsItems() {
        trySyncToRemote {
            val remoteTabs = remoteDataSource.getIngredientsTabsItems()
            localDataSource.saveIngredientsTabsListToDb(remoteTabs)
        }
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        if (id.isNullOrBlank()) {
            throw InvalidRecipeIdException()
        }
        return localDataSource.getLocalRecipeById(id)
    }

    fun getAllFavRecipes(): Flow<List<RecipeModel>> {
        return localDataSource.getLocalAllFavRecipesList()
    }

    suspend fun refreshFavRecipes() {
        trySyncToRemote {
            val remoteFavsRecipeList = remoteDataSource.getAllFavRecipesList()
            val remoteFavRecipesIds = remoteFavsRecipeList.map { FavRecipeModel(recipeId = it.id) }
            localDataSource.saveFavRecipesListToDb(remoteFavRecipesIds)
        }
    }

    suspend fun toggleFav(recipeId: String) {
        val currentlyFav = localDataSource.isFavLocally(recipeId)

        if (currentlyFav) {
            localDataSource.removeFavLocally(recipeId)
        } else {
            localDataSource.saveFavLocally(recipeId)
        }

        trySyncToRemote {
            remoteDataSource.toggleFav(recipeId)
            val isFavOnRemote = remoteDataSource.isFav(recipeId)
            if (isFavOnRemote) {
                localDataSource.markFavSynced(recipeId)
            }
        }
    }

    suspend fun syncPendingFavs() {
        trySyncToRemote {
            val unsyncedIds = localDataSource.getUnsyncedFavIds()
            unsyncedIds.forEach { recipeId ->
                remoteDataSource.toggleFav(recipeId)
                val isFavOnRemote = remoteDataSource.isFav(recipeId)
                if (isFavOnRemote) {
                    localDataSource.markFavSynced(recipeId)
                }
            }
        }
    }

    suspend fun isFav(recipeId: String): Boolean {
        return localDataSource.isFavLocally(recipeId)
    }

    suspend fun saveRecipe(recipeModel: CreateRecipeFormStateModel): Task<Void> {
        return remoteDataSource.saveRecipe(recipeModel)
    }

    suspend fun uploadImage(imageUri: Uri): String {
        return remoteDataSource.uploadImage(imageUri)
    }

    fun observeConnectivity(): Flow<Boolean> {
        return networkMonitor.observeConnectivity()
    }

    private suspend fun trySyncToRemote(action: suspend () -> Unit) {
        if (networkMonitor.isConnected()) {
            try {
                action()
            } catch (e: Exception) {
                Timber.e(e)  // sync failed silently — local data is already correct, will retry later
            }
        }
    }
}