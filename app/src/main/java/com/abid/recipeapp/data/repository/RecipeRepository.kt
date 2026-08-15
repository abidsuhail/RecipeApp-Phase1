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
import com.abid.recipeapp.util.datamapper.toRecipeModel
import com.abid.recipeapp.util.exception.InvalidRecipeIdException
import com.abid.recipeapp.util.isLocalUri
import com.abid.recipeapp.util.network.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.File
import java.util.UUID
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val localDataSource: RecipeLocalDataSource,
    private val remoteDataSource: RecipeRemoteDataSource,
    private val networkMonitor: NetworkMonitor
) {
    fun getAllRecipes(): Flow<List<RecipeModel>> {
        return localDataSource.getLocalAllRecipesList()
    }

    suspend fun refreshAllRecipes() {
        val remote = remoteDataSource.getAllRecipes()
        localDataSource.saveRecipeListToDb(remote, true)
    }

    fun getRecipeCategories(): Flow<List<RecipeCategoriesDataModel>> {
        val allCategoryItem = RecipeCategoriesDataModel("-1", "All")
        return localDataSource.getLocalAllCategoriesList().map { categories ->
            listOf(allCategoryItem) + categories
        }
    }


    suspend fun refreshCategories() {
        val remoteCategories = remoteDataSource.getRecipeCategories()
        localDataSource.saveCategoriesListToDb(remoteCategories)
    }

    fun getRecipesByFilters(
        categoryName: String? = null,
        cuisineName: String? = null,
        diffLvl: String? = null,
        title: String? = null
    ): Flow<List<RecipeModel>> {
        return localDataSource.getRecipesByFiltersLocal(
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
        val remoteCuisines = remoteDataSource.getCuisines()
        localDataSource.saveCuisinesListToDb(remoteCuisines)
    }


    suspend fun refreshRecipeDifficulties() {
        val remoteDiffLvls = remoteDataSource.getRecipeDifficulties()
        localDataSource.saveDiffLvlsListToDb(remoteDiffLvls)
    }


    suspend fun refreshIngredientsTabsItems() {
        val remoteTabs = remoteDataSource.getIngredientsTabsItems()
        localDataSource.saveIngredientsTabsListToDb(remoteTabs)
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
        val remoteFavsRecipeList = remoteDataSource.getAllFavRecipesList()
        val remoteFavRecipesIds =
            remoteFavsRecipeList.map { FavRecipeModel(recipeId = it.id) }
        localDataSource.saveFavRecipesListToDb(remoteFavRecipesIds)
    }

    suspend fun toggleFav(recipeId: String) {
        val currentlyFav = isFavLocally(recipeId).first()
        if (currentlyFav) {
            localDataSource.removeFavLocally(recipeId)
        } else {
            localDataSource.saveFavLocally(recipeId)
        }
        syncFavToRemoteAndLocal(recipeId)
    }

    suspend fun syncPendingFavs() {
        val unsyncedIds = localDataSource.getUnsyncedFavIds()
        unsyncedIds.forEach { recipeId ->
            syncFavToRemoteAndLocal(recipeId)
        }
    }

    private suspend fun syncFavToRemoteAndLocal(recipeId: String) {
        val fav = localDataSource.getLocalFavByRecipeId(recipeId)
        if (fav?.isFav == true) {
            remoteDataSource.saveFavRemote(recipeId)
        } else {
            remoteDataSource.removeFavRemote(recipeId)
        }
        localDataSource.markFavSynced(recipeId)
    }


    fun isFavLocally(recipeId: String): Flow<Boolean> {
        return localDataSource.isFavLocally(recipeId)
    }

    private fun generateNewRecipeId(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    private suspend fun uploadImgToCloudAndSyncDb(imageUri: Uri, recipeId: String) {
        val remoteImageUrl = remoteDataSource.uploadImage(imageUri)
        if (remoteImageUrl.isNotBlank()) {
            localDataSource.updateRecipeImageUrl(recipeId, remoteImageUrl)
            remoteDataSource.updateRecipeImageUrl(recipeId, remoteImageUrl)
            localDataSource.deleteRecipeImageLocal(recipeId)
        }
    }

    private suspend fun uploadImage(recipeId: String, imageUri: Uri): String {
        val localImagePath = localDataSource.copyImageToInternalStorage(recipeId, imageUri)
        try {
            if (networkMonitor.isConnected()) {
                uploadImgToCloudAndSyncDb(imageUri, recipeId)
            }
        } catch (e: Exception) {
            Timber.e("Image upload failed, using local path : $e")
        }
        return localImagePath
    }

    suspend fun saveRecipe(recipeModel: CreateRecipeFormStateModel, imageUri: Uri) {
        val recipeId = recipeModel.id.ifBlank { generateNewRecipeId() }
        val localImagePath = uploadImage(recipeId, imageUri)
        val finalRecipe = recipeModel.copy(id = recipeId, image = localImagePath).toRecipeModel()
        localDataSource.saveRecipeToDb(
            finalRecipe,
            false
        )
        if (networkMonitor.isConnected()) {
            val isSuccess = remoteDataSource.saveRecipe(finalRecipe)
            if (isSuccess) {
                localDataSource.markRecipeAsSynced(finalRecipe.id)
            }
        }
    }

    suspend fun syncAllPendingRecipesData() {
        val unsyncedRecipes = localDataSource.getUnsyncedRecipes()
        for (recipe in unsyncedRecipes) {
            try {
                remoteDataSource.saveRecipe(recipe)
                localDataSource.markRecipeAsSynced(recipe.id)
            } catch (e: Exception) {
                Timber.e("Recipe data sync failed for ${recipe.id}: $e")
            }
        }
    }


    suspend fun syncAllPendingRecipeImages() {
        val recipes = localDataSource.getLocalAllRecipesList()
            .first()
            .filter { it.image.isLocalUri() }
        for (recipe in recipes) {
            try {
                uploadImgToCloudAndSyncDb(Uri.fromFile(File(recipe.image)), recipe.id)
            } catch (e: Exception) {
                Timber.e("Image sync failed, using local path : $e")
            }
        }
    }


    /*   private suspend fun trySyncToRemote(action: suspend () -> Unit) {
              if (networkMonitor.isConnected()) {
                  try {
                      action()
                  } catch (e: Exception) {
                      Timber.e(e)  // sync failed silently — local data is already correct, will retry later
                  }
              }
       }*/
}