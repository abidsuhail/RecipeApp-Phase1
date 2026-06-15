package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel

data class SearchRecipeUiState(
    var isLoading: Boolean = true,
    var recipeList: List<RecipeModel> = emptyList(),
    var recipesCategories: List<RecipeCategoriesDataModel> = emptyList(),
    var recipesCuisines: List<RecipeCuisinesDataModel> = emptyList(),
    var recipeDifficulties: List<RecipeDiffLvlDataModel> = emptyList(),
    var errorMsg: String? = null
)