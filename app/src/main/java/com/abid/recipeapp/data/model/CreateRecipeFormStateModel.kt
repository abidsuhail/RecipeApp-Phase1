package com.abid.recipeapp.data.model

data class CreateRecipeFormStateModel(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val prepTime: Int = 0,
    val cookTime: Int = 0,
    val servings: Int = 0,
    val difficulty: RecipeDiffLvl = RecipeDiffLvl.EASY,
    val category: String = "",
    val cuisine: String = "",
    val author: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val tags: List<String> = emptyList(),
    val ingredients: List<IngredientFormStateModel> = emptyList(),
    val instructions: List<String> = emptyList(),
    val nutrition: NutritionFormStateModel = NutritionFormStateModel()
)

data class IngredientFormStateModel(
    val item: String = "",
    val amount: String = ""
)

data class NutritionFormStateModel(
    val calories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0
)