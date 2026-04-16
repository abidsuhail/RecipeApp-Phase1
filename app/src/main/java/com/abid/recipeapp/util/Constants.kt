package com.abid.recipeapp.util

import com.abid.recipeapp.data.model.RecipeModel

object Constants {
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

    object FirebaseCollections {
        const val RECIPES = "recipes"
        const val RECIPE_CATEGORIES = "recipe_categories"
        const val RECIPE_INGREDIENTS_INST_TAB = "ingredients_instructions_tabs"
        const val RECIPE_CUISINES = "recipe_cuisines"
        const val RECIPE_DIFFICULTY_LEVELS = "difficulty_levels"
    }
}


