package com.abid.recipeapp.util

import com.abid.recipeapp.data.model.RecipeModel

fun getTotalCookTimeInMin(recipe: RecipeModel): String {
    return "${(recipe.cookTime + recipe.prepTime)}min"
}

fun getServingsAndUrList(recipe: RecipeModel): List<String> {
    return listOf(
        "${recipe.cookTime + recipe.prepTime} min",
        "${recipe.servings} servings",
        recipe.difficulty.toString()
    )
}

val ingredientsAndInstTabItems = listOf("Ingredients", "Instructions")
val newRecipeCuisine = listOf(
    "American",
    "Italian",
    "Mexican",
    "Indian",
    "Chinese",
    "Thai",
    "Greek",
    "French",
    "Japanese"
)
val newRecipeDifficultyLvl = listOf(RecipeDiffLvl.EASY, RecipeDiffLvl.MEDIUM, RecipeDiffLvl.HARD)

