package com.abid.recipeapp.util


import com.abid.recipeapp.data.local.entities.RecipeCuisinesEntity
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel

fun RecipeCuisinesDataModel.toEntity(): RecipeCuisinesEntity = RecipeCuisinesEntity(
    id = id,
    label = label
)

fun RecipeCuisinesEntity.toModel(): RecipeCuisinesDataModel = RecipeCuisinesDataModel(
    id = id,
    label = label
)
