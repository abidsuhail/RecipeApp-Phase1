package com.abid.recipeapp.util.datamapper

import com.abid.recipeapp.data.local.entities.RecipeDiffLvlEntity
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel

fun RecipeDiffLvlDataModel.toEntity(): RecipeDiffLvlEntity = RecipeDiffLvlEntity(
    id = id,
    label = label
)

fun RecipeDiffLvlEntity.toModel(): RecipeDiffLvlDataModel = RecipeDiffLvlDataModel(
    id = id,
    label = label
)