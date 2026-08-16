package com.abid.recipeapp.data.local

import android.content.Context
import android.net.Uri
import com.abid.recipeapp.data.local.dao.CategoriesDao
import com.abid.recipeapp.data.local.dao.CuisinesDao
import com.abid.recipeapp.data.local.dao.DiffLvlDao
import com.abid.recipeapp.data.local.dao.FavRecipeDao
import com.abid.recipeapp.data.local.dao.IngredientsTabDao
import com.abid.recipeapp.data.local.dao.RecipeDao
import com.abid.recipeapp.data.local.entities.FavRecipeEntity
import com.abid.recipeapp.data.model.FavRecipeModel
import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.datamapper.toEntity
import com.abid.recipeapp.util.datamapper.toModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.File

class RecipeLocalDataSource(
    private val recipeDao: RecipeDao,
    private val cuisinesDao: CuisinesDao,
    private val categoriesDao: CategoriesDao,
    private val diffLvlDao: DiffLvlDao,
    private val ingredientsTabDao: IngredientsTabDao,
    private val favRecipeDao: FavRecipeDao,
    @ApplicationContext private val context: Context
) {

    /*    suspend fun saveRecipeToDb(recipeModel: RecipeModel) {
            recipeDao.insert(recipeModel.com.abid.recipeapp.util.datamapper.toEntity())
        }*/

    suspend fun saveCuisinesListToDb(cuisinesModels: List<RecipeCuisinesDataModel>) {
        cuisinesDao.insertAll(cuisinesModels.map { it.toEntity() })
    }

    fun getLocalAllCuisinesList(): Flow<List<RecipeCuisinesDataModel>> {
        return cuisinesDao.getAllCuisines().map { list ->
            list.map { it.toModel() }
        }
    }

    suspend fun saveCategoriesListToDb(categoriesModels: List<RecipeCategoriesDataModel>) {
        categoriesDao.insertAll(categoriesModels.map { it.toEntity() })
    }

    fun getLocalAllCategoriesList(): Flow<List<RecipeCategoriesDataModel>> {
        return categoriesDao.getAllCategories().map { entityList ->
            entityList.map { it.toModel() }
        }
    }

    suspend fun saveRecipeListToDb(recipeModels: List<RecipeModel>, isSynced: Boolean) {
        recipeDao.insertAll(recipeModels.map { it.toEntity(isSynced) })
    }

    suspend fun saveRecipeToDb(recipeModel: RecipeModel, isSynced: Boolean) {
        recipeDao.insert(recipeModel.toEntity(isSynced))
    }

    fun getLocalAllRecipesList(): Flow<List<RecipeModel>> {
        return recipeDao.getAllRecipes().map { entityList ->
            entityList.map { it.toModel() }
        }
    }

    suspend fun updateRecipeImageUrl(recipeId: String, imageUrl: String) {
        recipeDao.updateRecipeImage(recipeId, imageUrl)
    }

    suspend fun getLocalRecipeById(id: String): RecipeModel? {
        return recipeDao.getRecipeById(id)?.toModel()
    }

    suspend fun markRecipeAsSynced(recipeId: String) {
        recipeDao.markAsSynced(recipeId)
    }

    suspend fun markRecipeAsUnSynced(recipeId: String) {
        recipeDao.markAsUnsynced(recipeId)
    }

    suspend fun getUnsyncedRecipes(): List<RecipeModel> {
        return recipeDao.getUnsyncedRecipes().map { it.toModel() }
    }

    fun getRecipesByFiltersLocal(
        categoryName: String? = null,
        cuisineName: String? = null,
        diffLvl: String? = null,
        title: String? = null
    ): Flow<List<RecipeModel>> {
        return recipeDao.getRecipesByFilters(categoryName, cuisineName, diffLvl, title)
            .map { entityList -> entityList.map { it.toModel() } }
    }

    suspend fun saveDiffLvlsListToDb(diffLvlModels: List<RecipeDiffLvlDataModel>) {
        diffLvlDao.insertAll(diffLvlModels.map { it.toEntity() })
    }

    fun getLocalAllDiffLvlsList(): Flow<List<RecipeDiffLvlDataModel>> {
        return diffLvlDao.getAllDiffLvls().map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    suspend fun saveIngredientsTabsListToDb(tabModels: List<IngredientsTabModel>) {
        ingredientsTabDao.insertAll(tabModels.map { it.toEntity() })
    }

    fun getLocalAllIngredientsTabsList(): Flow<List<IngredientsTabModel>> {
        return ingredientsTabDao.getAllIngredientsTabs().map { list ->
            list.map {
                it.toModel()
            }
        }
    }


    suspend fun saveFavRecipesListToDb(recipes: List<FavRecipeModel>) {
        favRecipeDao.insertAll(recipes.map { it.toEntity() })
    }

    fun getLocalAllFavRecipesList(): Flow<List<RecipeModel>> {
        return favRecipeDao.getAllFavRecipesFlow().map { entityList ->
            entityList.map { it.toModel() }
        }
    }

    suspend fun saveFavLocally(recipeId: String): Boolean {
        val isInserted = favRecipeDao.insert(FavRecipeEntity(recipeId = recipeId, isFav = true))
        return isInserted != -1L
    }

    suspend fun removeFavLocally(recipeId: String): Boolean {
        val isInserted = favRecipeDao.insert(FavRecipeEntity(recipeId = recipeId, isFav = false))
        return isInserted != -1L
        /*    val rowsDeleted = favRecipeDao.delete(FavRecipeEntity(recipeId = recipeId, isFav = false))
            return rowsDeleted > 0*/
    }

    fun isFavLocally(recipeId: String): Flow<Boolean> {
        return favRecipeDao.getFavByRecipeId(recipeId).map { it?.isFav == true }
    }

    suspend fun getLocalFavByRecipeId(recipeId: String): FavRecipeEntity? {
        return favRecipeDao.getFavByRecipeId(recipeId).firstOrNull()
    }


    suspend fun getUnsyncedFavIds(): List<String> {
        return favRecipeDao.getUnsyncedFavIds()
    }

    suspend fun markFavSynced(recipeId: String) {
        favRecipeDao.markFavSynced(recipeId)
    }


    fun copyImageToInternalStorage(recipeId: String, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.filesDir, "pending_images/$recipeId.jpg")
        file.parentFile?.mkdirs()
        inputStream?.use { input ->
            file.outputStream().use { output -> input.copyTo(output) }
        }
        return file.absolutePath
    }

    fun deleteRecipeImageLocal(recipeId: String) {
        val file = File(context.filesDir, "pending_images/$recipeId.jpg")
        if (file.exists()) {
            file.delete()
        }
    }

}