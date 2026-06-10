package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.ui.components.RecipeCatChipItem
import com.abid.recipeapp.ui.components.RecipeList
import com.abid.recipeapp.ui.state.RecipeListUiState
import com.abid.recipeapp.util.Constants
import com.abid.recipeapp.util.Constants.ADD_BUTTON
import com.abid.recipeapp.util.Constants.DISCOVER_RECIPES
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.viewmodel.RecipeListViewModel

@Composable
fun RecipeListScreen(
    recipeListViewModel: RecipeListViewModel = hiltViewModel(),
    onClickRecipeItem: (RecipeModel) -> Unit,
    onClickAddRecipe: () -> Unit
) {
    val recipeListState by recipeListViewModel.state.observeAsState()

    LaunchedEffect(Unit) {
        recipeListViewModel.loadAllRecipes()
        recipeListViewModel.loadRecipeCategories()
    }
    Box() {
        Column(modifier = Modifier.padding(top = Dims.Dp10)) {
            RecipeListHeader(onClickAddRecipe)
            RecipeCategoriesList(recipeListState) { category ->
                recipeListViewModel.loadAllRecipes(category)
            }
            if (recipeListState?.isLoading == true) {
                LoadingStateBox()
            } else if (!recipeListState?.errorMsg.isNullOrBlank()) {
                ErrorStateBox(recipeListState!!.errorMsg!!)
            } else if (recipeListState?.recipeList?.isNotEmpty() == true) {
                RecipeList(
                    recipeList = recipeListState?.recipeList,
                    onClickRecipeItem = onClickRecipeItem
                )
            } else if (recipeListState?.recipeList?.isEmpty() == true) {
                ErrorStateBox(Constants.RECIPE_NOT_FOUND_ERROR_MSG)
            }

        }
    }
}

@Composable
private fun RecipeCategoriesList(
    state: RecipeListUiState?,
    onClickRecipeCat: (String?) -> Unit
) {
    if (state?.recipesCategories?.isNotEmpty() == true) {
        val recipeCategories = state.recipesCategories
        var selectedCategory by remember { mutableStateOf<String?>(null) }
        LazyRow {
            items(items = recipeCategories, key = { recipeCat -> recipeCat.id }) { recipeCat ->
                RecipeCatChipItem(
                    recipeCat = recipeCat,
                    isSelected = if (selectedCategory == null) {
                        recipeCat.id == "-1"
                    } else {
                        recipeCat.label == selectedCategory
                    }
                )
                { clickedRecipe ->
                    selectedCategory = if (clickedRecipe.id == "-1") null else recipeCat.label
                    onClickRecipeCat(selectedCategory)
                }
            }
        }
    }
}


@Composable
private fun RecipeListHeader(onClickAddRecipe: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dims.Dp5)
    ) {
        Text(DISCOVER_RECIPES, fontSize = Dims.Sp30)
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = ADD_BUTTON,
            modifier = Modifier
                .size(Dims.Dp50)
                .clickable(
                    onClick = {
                        onClickAddRecipe()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current,
                ),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

