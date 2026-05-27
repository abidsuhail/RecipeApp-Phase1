package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeModel

data class RecipeDetailsUiState(
    var isLoading: Boolean = false,
    var ingredientsTabList: List<IngredientsTabModel> = emptyList(),
    var isFav: Boolean = false,
    var recipe: RecipeModel? = null,
    var errorMsg: String? = null
)

