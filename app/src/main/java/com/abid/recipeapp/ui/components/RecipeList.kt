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

@Composable
fun RecipeList(
    recipeList: List<RecipeModel>?,
    onClickRecipeItem: (RecipeModel) -> Unit,
) {

    LazyColumn {
        items(items = recipeList ?: emptyList(), key = { recipe -> recipe.id }) { recipe ->
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

}