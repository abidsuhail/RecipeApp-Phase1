package com.abid.recipeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeModel(
    val id: String = "",
    var title: String = "",
    var description: String = "",
    var image: String = "",
    val prepTime: Int = 0,
    val cookTime: Int = 0,
    val servings: Int = 0,
    var difficulty: RecipeDiffLvl = RecipeDiffLvl.EASY,  // ⚠️ Change enum → String
    var category: String = "",
    var cuisine: String = "",
    val author: String = "",
    val createdAt: String = "",
    val tags: List<String> = emptyList(),
    val ingredients: List<IngredientModel> = emptyList(),
    val instructions: List<String> = emptyList(),
    val nutrition: NutritionModel = NutritionModel()
) : Parcelable

@Parcelize
data class IngredientModel(
    val item: String = "",
    val amount: String = ""
) : Parcelable

@Parcelize
data class NutritionModel(
    val calories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0
) : Parcelable