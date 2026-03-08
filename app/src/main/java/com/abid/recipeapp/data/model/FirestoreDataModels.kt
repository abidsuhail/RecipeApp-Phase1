package com.abid.recipeapp.data.model

import com.abid.recipeapp.util.RecipeDiffLvl

data class IngredientsTabModel(
    val id: String = "",
    val label: String = ""
)

data class RecipeCategoriesDataModel(
    val id: String = "",
    val label: String = ""
)

data class RecipeCuisinesDataModel(
    val id: String = "",
    val label: String = ""
)

data class RecipeDiffLvlDataModel(
    val id: String = "",
    val label: RecipeDiffLvl = RecipeDiffLvl.EASY
)