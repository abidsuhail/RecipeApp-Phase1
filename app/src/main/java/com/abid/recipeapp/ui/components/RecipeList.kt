package com.abid.recipeapp.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.Constants

@Composable
fun RecipeList(
    isLoading: Boolean?,
    errorMsg: String?,
    recipeList: List<RecipeModel>?,
    onClickRecipeItem: (RecipeModel) -> Unit,
) {

    if (isLoading == true) {
        LoadingStateBox()
    } else if (!errorMsg.isNullOrBlank()) {
        ErrorStateBox(errorMsg)
    } else if (recipeList?.isNotEmpty() == true) {
        LazyColumn {
            items(items = recipeList, key = { recipe -> recipe.id }) { recipe ->
                Box(
                    modifier = Modifier.clickable(
                        onClick = {
                            onClickRecipeItem(recipe)
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current,
                    )
                ) {
                    RecipeCard(recipe)
                }
            }
        }
    } else if (recipeList?.isEmpty() == true) {
        ErrorStateBox(Constants.RECIPE_NOT_FOUND_ERROR_MSG)
    }

}