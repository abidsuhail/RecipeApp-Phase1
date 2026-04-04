package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeModel

data class RecipeListUiState(
    var isLoading: Boolean = true,
    var recipeList: List<RecipeModel> = emptyList(),
    var recipesCategories: List<RecipeCategoriesDataModel> = emptyList(),
    var errorMsg: String? = null
)