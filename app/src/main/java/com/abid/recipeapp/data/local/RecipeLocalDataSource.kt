package com.abid.recipeapp.data.local

import com.abid.recipeapp.data.local.dao.CategoriesDao
import com.abid.recipeapp.data.local.dao.CuisinesDao
import com.abid.recipeapp.data.local.dao.DiffLvlDao
import com.abid.recipeapp.data.local.dao.IngredientsTabDao
import com.abid.recipeapp.data.local.dao.RecipeDao
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
    private val ingredientsTabDao: IngredientsTabDao
) {

    suspend fun saveRecipeToDb(recipeModel: RecipeModel) {
        recipeDao.insert(recipeModel.toEntity())
    }

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


}