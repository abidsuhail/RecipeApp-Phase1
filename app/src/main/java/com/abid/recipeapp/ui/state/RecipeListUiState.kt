package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeModel

sealed class RecipeListUiState {
    data object Loading : RecipeListUiState()
    data class Success(val recipes: List<RecipeModel>) : RecipeListUiState()
    data class Error(val message: String) : RecipeListUiState()
}