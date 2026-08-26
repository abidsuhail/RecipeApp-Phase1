package com.abid.recipeapp.util.datamapper

import com.abid.recipeapp.data.model.CreateRecipeFormStateModel
import com.abid.recipeapp.data.model.IngredientFormStateModel
import com.abid.recipeapp.data.model.IngredientModel
import com.abid.recipeapp.data.model.NutritionFormStateModel
import com.abid.recipeapp.data.model.NutritionModel
import com.abid.recipeapp.data.model.RecipeModel

fun CreateRecipeFormStateModel.toRecipeModel(): RecipeModel = RecipeModel(
    id = id,
    title = title,
    description = description,
    image = image,
    prepTime = prepTime,
    cookTime = cookTime,
    servings = servings,
    difficulty = difficulty,
    category = category,
    cuisine = cuisine,
    author = author,
    createdAt = createdAt,
    tags = tags,
    ingredients = ingredients.map { it.toIngredientModel() },
    instructions = instructions,
    nutrition = nutrition.toNutritionModel()
)

fun IngredientFormStateModel.toIngredientModel(): IngredientModel = IngredientModel(
    item = item,
    amount = amount
)

fun NutritionFormStateModel.toNutritionModel(): NutritionModel = NutritionModel(
    calories = calories,
    protein = protein,
    carbs = carbs,
    fat = fat
)