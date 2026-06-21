package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.RecipeModel

data class FavUiState(
    var isLoading: Boolean = true,
    var favRecipeList: List<RecipeModel> = emptyList(),
    var errorMsg: String? = null
)
