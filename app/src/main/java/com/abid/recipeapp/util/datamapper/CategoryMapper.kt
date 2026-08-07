package com.abid.recipeapp.util.datamapper

import com.abid.recipeapp.data.local.entities.RecipeCategoriesEntity
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel

fun RecipeCategoriesDataModel.toEntity(): RecipeCategoriesEntity = RecipeCategoriesEntity(
    id = id,
    label = label
)

fun RecipeCategoriesEntity.toModel(): RecipeCategoriesDataModel = RecipeCategoriesDataModel(
    id = id,
    label = label
)