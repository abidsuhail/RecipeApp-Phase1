package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel

data class SearchFilterOptionsUiState(
    var isCategoriesLoading: Boolean = false,
    var isCuisinesLoading: Boolean = false,
    var isDiffLvlLoading: Boolean = false,

    val recipeCategories: List<RecipeCategoriesDataModel> = emptyList(),
    val recipeCuisines: List<RecipeCuisinesDataModel> = emptyList(),
    val recipeDifficulties: List<RecipeDiffLvlDataModel> = emptyList(),

    var categoriesErrorMsg: String? = null,
    var cuisinesErrorMsg: String? = null,
    var diffLvlErrorMsg: String? = null
)

data class SearchResultUiState(
    var isLoading: Boolean = false,
    var recipeList: List<RecipeModel> = emptyList(),
    var errorMsg: String? = null,
)