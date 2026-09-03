package com.abid.recipeapp.util.datamapper.graphql

import com.abid.recipeapp.FavoritesQuery
import com.abid.recipeapp.data.model.IngredientModel
import com.abid.recipeapp.data.model.NutritionModel
import com.abid.recipeapp.data.model.RecipeDiffLvl
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.toRecipeDiffLvl

fun FavoritesQuery.Recipe.toRecipeModel(): RecipeModel {
    return RecipeModel(
        id = id,
        title = title,
        description = description ?: "",
        image = image ?: "",
        prepTime = prepTime ?: 0,
        cookTime = cookTime ?: 0,
        servings = servings ?: 0,
        difficulty = difficulty?.toRecipeDiffLvl() ?: RecipeDiffLvl.EASY,
        category = category ?: "",
        cuisine = cuisine ?: "",
        author = author ?: "",
        createdAt = createdAt?.toLongOrNull() ?: 0L,
        tags = tags,
        ingredients = ingredients.map { it.toIngredientModel() },
        instructions = instructions,
        nutrition = nutrition?.toNutritionModel() ?: NutritionModel(),
    )
}


fun FavoritesQuery.Ingredient.toIngredientModel(): IngredientModel {
    return IngredientModel(
        item = item,
        amount = amount ?: "",
    )
}

fun FavoritesQuery.Nutrition.toNutritionModel(): NutritionModel {
    return NutritionModel(
        calories = calories ?: 0,
        protein = protein ?: 0,
        carbs = carbs ?: 0,
        fat = fat ?: 0,
    )
}