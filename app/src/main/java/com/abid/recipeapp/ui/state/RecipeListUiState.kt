package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeModel

sealed class RecipeListUiState {
    data object Loading : RecipeListUiState()
    data class RecipeListSuccess(val recipes: List<RecipeModel>) : RecipeListUiState()
    data class RecipeCategoriesSuccess(val recipesCategories: List<RecipeCategoriesDataModel>) :
        RecipeListUiState()

    data class Error(val message: String) : RecipeListUiState()
}