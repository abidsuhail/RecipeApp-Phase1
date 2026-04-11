package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.CreateRecipeFormStateModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel

sealed class RecipeSaveState {
    data object Idle : RecipeSaveState()
    data class Success(val recipe: CreateRecipeFormStateModel) : RecipeSaveState()
}

data class CreateRecipeUiState(
    var isLoading: Boolean = false,
    var cuisinesList: List<RecipeCuisinesDataModel> = emptyList(),
    var difficultiesList: List<RecipeDiffLvlDataModel> = emptyList(),
    var categoriesList: List<RecipeCategoriesDataModel> = emptyList(),
    var recipeModel: CreateRecipeFormStateModel = CreateRecipeFormStateModel(),
    var recipeSaveState: RecipeSaveState = RecipeSaveState.Idle,
    var uploadedImageUrl: String? = null,
    var errorMsg: String? = null
)

