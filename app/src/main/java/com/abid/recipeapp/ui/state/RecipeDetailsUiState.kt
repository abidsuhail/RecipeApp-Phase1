package com.abid.recipeapp.ui.state

import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeModel

data class RecipeDetailsUiState(
    val isRecipeLoading: Boolean = false,
    val recipe: RecipeModel? = null,
    val recipeErrorMsg: String? = null,

    val isIngredientsTabsLoading: Boolean = false,
    val ingredientsTabList: List<IngredientsTabModel> = emptyList(),
    val ingredientsTabsErrorMsg: String? = null,

    val isFavLoading: Boolean = false,
    val isFav: Boolean = false,
    val favErrorMsg: String? = null
)

