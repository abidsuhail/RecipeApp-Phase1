package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeModel

sealed class RecipeDetailUiState {
    data object Loading : RecipeDetailUiState()
    data class Success(val recipe: RecipeModel) : RecipeDetailUiState()
    data class Error(val message: String) : RecipeDetailUiState()
    data object NotFound : RecipeDetailUiState()
}