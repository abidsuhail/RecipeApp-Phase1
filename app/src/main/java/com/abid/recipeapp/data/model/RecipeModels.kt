package com.abid.recipeapp.data.model

data class RecipeModel(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val prepTime: Int,
    val cookTime: Int,
    val servings: Int,
    val difficulty: String,
    val category: String,
    val cuisine: String,
    val author: String,
    val createdAt: String,
    val tags: List<String>,
    val ingredients: List<IngredientModel>,
    val instructions: List<String>,
    val nutrition: NutritionModel
)

data class IngredientModel(
    val item: String,
    val amount: String
)

data class NutritionModel(
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int
)
