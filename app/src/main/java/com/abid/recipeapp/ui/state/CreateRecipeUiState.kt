package com.abid.recipeapp.ui.state


data class CreateRecipeUiState<T>(
    val isLoading: Boolean = false,
    val data: List<T> = emptyList(),
    val error: String? = null
)

data class CreateRecipeUiStates(
    val categories: CreateRecipeUiState<String> = CreateRecipeUiState(),
    val cuisines: CreateRecipeUiState<String> = CreateRecipeUiState(),
    val difficulties: CreateRecipeUiState<String> = CreateRecipeUiState()
)