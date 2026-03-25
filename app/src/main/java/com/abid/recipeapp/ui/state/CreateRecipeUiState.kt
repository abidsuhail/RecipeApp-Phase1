package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel

sealed class RecipeSaveState {
    data object Idle : RecipeSaveState()
    data class Success(val recipe: RecipeModel) : RecipeSaveState()
}

data class CreateRecipeUiState(
    var isLoading: Boolean = false,
    var cuisinesList: List<RecipeCuisinesDataModel> = emptyList(),
    var difficultiesList: List<RecipeDiffLvlDataModel> = emptyList(),
    var categoriesList: List<RecipeCategoriesDataModel> = emptyList(),
    var recipeSaveState: RecipeSaveState = RecipeSaveState.Idle,
    var uploadedImageUrl: String? = null,
    var errorMsg: String? = null
)

