package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeModel

data class RecipeListUiState(
    val isRecipesLoading: Boolean = true,
    val recipeList: List<RecipeModel> = emptyList(),
    val recipesErrorMsg: String? = null,

    val isCategoriesLoading: Boolean = true,
    val recipesCategories: List<RecipeCategoriesDataModel> = emptyList(),
    val categoriesErrorMsg: String? = null
)