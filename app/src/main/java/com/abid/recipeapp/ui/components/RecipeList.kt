package com.abid.recipeapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.abid.recipeapp.util.MockData

@Composable
fun RecipeList(){
    LazyColumn {
        items(items = MockData.mockRecipes){ recipe->
            RecipeCard(recipe)
        }
    }
}