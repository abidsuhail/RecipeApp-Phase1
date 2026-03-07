package com.abid.recipeapp.ui.state

import com.abid.recipeapp.RecipeCategoriesDataModel
import com.abid.recipeapp.util.RecipeDiffLvl


sealed class RecipeCategoriesUiState {
    data object Loading : RecipeCategoriesUiState()
    data class Success(val catList: List<RecipeCategoriesDataModel>) : RecipeCategoriesUiState()
    data class Error(val message: String) : RecipeCategoriesUiState()
    data class NotFound(val message: String) : RecipeCategoriesUiState()
}

sealed class RecipeCuisinesUiState {
    data object Loading : RecipeCuisinesUiState()
    data class Success(val cuisinesList: List<String>) : RecipeCuisinesUiState()
    data class Error(val message: String) : RecipeCuisinesUiState()
    data class NotFound(val message: String) : RecipeCuisinesUiState()
}

sealed class RecipeDifficultiesUiState {
    data object Loading : RecipeDifficultiesUiState()
    data class Success(val difficultiesList: List<RecipeDiffLvl>) : RecipeDifficultiesUiState()
    data class Error(val message: String) : RecipeDifficultiesUiState()
    data class NotFound(val message: String) : RecipeDifficultiesUiState()
}
