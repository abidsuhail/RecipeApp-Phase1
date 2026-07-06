package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel

data class SearchRecipeUiState(
    var isSearchLoading: Boolean = false,
    var isCategoriesLoading: Boolean = false,
    var isCuisinesLoading: Boolean = false,
    var isDiffLvlLoading: Boolean = false,

    var recipeList: List<RecipeModel> = emptyList(),
    var recipesCategories: List<RecipeCategoriesDataModel> = emptyList(),
    var recipesCuisines: List<RecipeCuisinesDataModel> = emptyList(),
    var recipeDifficulties: List<RecipeDiffLvlDataModel> = emptyList(),

    var searchErrorMsg: String? = null,
    var categoriesErrorMsg: String? = null,
    var cuisinesErrorMsg: String? = null,
    var diffLvlErrorMsg: String? = null


)