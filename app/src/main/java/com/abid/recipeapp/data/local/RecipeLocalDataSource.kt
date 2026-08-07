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

    private suspend fun saveRecipeToDb(recipeModel: RecipeModel) {
        recipeDao.insert(recipeModel.toEntity())
    }

    private suspend fun saveRecipeListToDb(recipeModels: List<RecipeModel>) {
        recipeDao.insertAll(recipeModels.map { it.toEntity() })
    }

    private suspend fun getLocalAllRecipesList(): List<RecipeModel> {
        return recipeDao.getAllRecipes().value?.map { it.toModel() } ?: emptyList()
    }

    private suspend fun getLocalAllCuisinesList(): List<RecipeCuisinesDataModel> {
        return cuisinesDao.getAllCuisines().value?.map { it.toModel() }
            ?: emptyList()
    }
}