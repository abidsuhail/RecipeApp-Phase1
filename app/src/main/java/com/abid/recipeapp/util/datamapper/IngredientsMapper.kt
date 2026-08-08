package com.abid.recipeapp.util.datamapper

import com.abid.recipeapp.data.local.entities.IngredientsTabEntity
import com.abid.recipeapp.data.model.IngredientsTabModel

fun IngredientsTabModel.toEntity(): IngredientsTabEntity = IngredientsTabEntity(
    id = id,
    label = label
)

fun IngredientsTabEntity.toModel(): IngredientsTabModel = IngredientsTabModel(
    id = id,
    label = label
)