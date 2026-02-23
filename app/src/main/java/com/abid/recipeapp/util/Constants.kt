package com.abid.recipeapp.util

import com.abid.recipeapp.data.model.RecipeModel

fun getTotalCookTimeInMin(recipe: RecipeModel): String {
    return "${(recipe.cookTime + recipe.prepTime)}min"
}