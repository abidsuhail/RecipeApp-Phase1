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
        const val FAV = "fav"
        const val RECIPE_CATEGORIES = "recipe_categories"
        const val RECIPE_INGREDIENTS_INST_TAB = "ingredients_instructions_tabs"
        const val RECIPE_CUISINES = "recipe_cuisines"
        const val RECIPE_DIFFICULTY_LEVELS = "difficulty_levels"
    }

    const val RECIPE_NOT_FOUND_ERROR_MSG = "Recipe not found"
    const val RECIPE_APP_INFO =
        "Recipe app is your personal cooking companion. Discover,save, and create delicious recipes from around the world"
    const val RECIPE_TITLE_CANNOT_BE_EMPTY = "Recipe Title cannot be empty"
    const val DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be empty"
    const val FAVOURITES_SCREEN_HEADING = "Favourites"
    const val FAVORITE_CONTENT_DESC = "Favorite"
    const val DISCOVER_RECIPES = "Discover Recipes"
    const val SEARCH_RECIPE_SCREEN = "Search recipe screen"
    const val ABOUT = "About"
    const val VERSION = "Version"
    const val TOTAL_RECIPES = "Total Recipes"
    const val APP_INFO = "App Info"
    const val THEME = "Theme"
    const val CHOOSE_YOUR_PREFERRED_THEME = "Choose your preferred theme"
    const val BACK_IMG = "Back img"
    const val RECIPE_IMAGE = "Recipe image"
    const val INFO_ICON = "Info icon"
    const val FACE_ICON = "Face icon"
    const val BACK_BUTTON = "Back button"
    const val CREATE_RECIPE = "Create Recipe"
    const val CLICK_HERE_TO_PICK_FROM_GALLERY = "Click here to pick from gallery"
    const val INGREDIENT_NAME = "Ingredient name"
    const val AMOUNT = "Amount"
    const val CLOSE_ICON = "Close icon"

    const val SETTINGS_DATASTORE_NAME = "settings"

}


