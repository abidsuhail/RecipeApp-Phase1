package com.abid.recipeapp.data.local

import com.abid.recipeapp.data.local.dao.CuisinesDao
import com.abid.recipeapp.data.local.dao.RecipeDao
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.datamapper.toEntity
import com.abid.recipeapp.util.datamapper.toModel

class RecipeLocalDataSource(
    private val recipeDao: RecipeDao,
    private val cuisinesDao: CuisinesDao,
) {

    suspend fun saveRecipeToDb(recipeModel: RecipeModel) {
        recipeDao.insert(recipeModel.toEntity())
    }

    suspend fun saveCuisinesListToDb(cuisinesModels: List<RecipeCuisinesDataModel>) {
        cuisinesDao.insertAll(cuisinesModels.map { it.toEntity() })
    }

    suspend fun saveRecipeListToDb(recipeModels: List<RecipeModel>) {
        recipeDao.insertAll(recipeModels.map { it.toEntity() })
    }

    suspend fun getLocalAllRecipesList(): List<RecipeModel> {
        return recipeDao.getAllRecipes().map { it.toModel() }
    }

    suspend fun getLocalAllCuisinesList(): List<RecipeCuisinesDataModel> {
        return cuisinesDao.getAllCuisines().map { it.toModel() }
    }
}