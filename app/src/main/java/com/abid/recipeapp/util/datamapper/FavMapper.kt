package com.abid.recipeapp.util.datamapper

import com.abid.recipeapp.data.local.entities.FavRecipeEntity
import com.abid.recipeapp.data.model.FavRecipeModel

fun FavRecipeModel.toEntity(): FavRecipeEntity =
    FavRecipeEntity(recipeId = recipeId, isFav = true, isSynced = true)

fun FavRecipeEntity.toModel(): FavRecipeModel =
    FavRecipeModel(recipeId = recipeId)