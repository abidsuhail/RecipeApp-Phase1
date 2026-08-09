package com.abid.recipeapp.data.local

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

class RecipeLocalDataSource(
    private val recipeDao: RecipeDao,
    private val cuisinesDao: CuisinesDao,
    private val categoriesDao: CategoriesDao,
    private val diffLvlDao: DiffLvlDao,
    private val ingredientsTabDao: IngredientsTabDao,
    private val favRecipeDao: FavRecipeDao
) {

    /*    suspend fun saveRecipeToDb(recipeModel: RecipeModel) {
            recipeDao.insert(recipeModel.toEntity())
        }*/

    suspend fun saveCuisinesListToDb(cuisinesModels: List<RecipeCuisinesDataModel>) {
        cuisinesDao.insertAll(cuisinesModels.map { it.toEntity() })
    }

    suspend fun getLocalAllCuisinesList(): List<RecipeCuisinesDataModel> {
        return cuisinesDao.getAllCuisines().map { it.toModel() }
    }

    suspend fun saveCategoriesListToDb(categoriesModels: List<RecipeCategoriesDataModel>) {
        categoriesDao.insertAll(categoriesModels.map { it.toEntity() })
    }

    suspend fun getLocalAllCategoriesList(): List<RecipeCategoriesDataModel> {
        return categoriesDao.getAllCategories().map { it.toModel() }
    }

    suspend fun saveRecipeListToDb(recipeModels: List<RecipeModel>) {
        recipeDao.insertAll(recipeModels.map { it.toEntity() })
    }

    suspend fun getLocalAllRecipesList(): List<RecipeModel> {
        return recipeDao.getAllRecipes().map { it.toModel() }
    }

    suspend fun getLocalRecipeById(id: String): RecipeModel? {
        return recipeDao.getRecipeById(id)?.toModel()
    }

    suspend fun saveDiffLvlsListToDb(diffLvlModels: List<RecipeDiffLvlDataModel>) {
        diffLvlDao.insertAll(diffLvlModels.map { it.toEntity() })
    }

    suspend fun getLocalAllDiffLvlsList(): List<RecipeDiffLvlDataModel> {
        return diffLvlDao.getAllDiffLvls().map { it.toModel() }
    }

    suspend fun saveIngredientsTabsListToDb(tabModels: List<IngredientsTabModel>) {
        ingredientsTabDao.insertAll(tabModels.map { it.toEntity() })
    }

    suspend fun getLocalAllIngredientsTabsList(): List<IngredientsTabModel> {
        return ingredientsTabDao.getAllIngredientsTabs().map { it.toModel() }
    }

    suspend fun saveFavRecipesIdsToDb(recipes: List<FavRecipeModel>) {
        favRecipeDao.insertAll(recipes.map { it.toEntity() })
    }

    suspend fun getLocalAllFavRecipesList(): List<RecipeModel> {
        val favIds = favRecipeDao.getAllFavRecipeIds()
        if (favIds.isEmpty()) return emptyList()

        return recipeDao.getAllRecipes()
            .filter { it.id in favIds }
            .map { it.toModel() }
    }

    suspend fun saveFavLocally(recipeId: String): Boolean {
        val isInserted = favRecipeDao.insert(FavRecipeEntity(recipeId = recipeId))
        return isInserted == -1L
    }

    suspend fun isFavLocally(recipeId: String): Boolean {
        return favRecipeDao.isFav(recipeId) != null
    }

    suspend fun removeFavLocally(recipeId: String): Boolean {
        val rowsDeleted = favRecipeDao.delete(FavRecipeEntity(recipeId = recipeId))
        return rowsDeleted > 0
    }

}