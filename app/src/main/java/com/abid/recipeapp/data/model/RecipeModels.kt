package com.abid.recipeapp.data.model

import android.os.Parcelable
import com.abid.recipeapp.util.RecipeDiffLvl
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeModel(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val prepTime: Int,
    val cookTime: Int,
    val servings: Int,
    val difficulty: RecipeDiffLvl,
    val category: String,
    val cuisine: String,
    val author: String,
    val createdAt: String,
    val tags: List<String>,
    val ingredients: List<IngredientModel>,
    val instructions: List<String>,
    val nutrition: NutritionModel
) : Parcelable

@Parcelize
data class IngredientModel(
    val item: String,
    val amount: String
) : Parcelable

@Parcelize
data class NutritionModel(
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int
) : Parcelable
