package com.abid.recipeapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abid.recipeapp.data.local.converters.RecipeConverters
import com.abid.recipeapp.data.model.RecipeDiffLvl

@Entity(tableName = "recipes")
@TypeConverters(RecipeConverters::class)
data class RecipeEntity(
    @PrimaryKey val id: String = "",
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
    val createdAt: Long = 0L,
    val tags: List<String> = emptyList(),
    val ingredients: List<IngredientEntity> = emptyList(),
    val instructions: List<String> = emptyList(),
    val isDataSynced: Boolean = true,
    @Embedded(prefix = "nutrition_") val nutrition: NutritionEntity = NutritionEntity()
)

data class IngredientEntity(
    val item: String = "",
    val amount: String = ""
)

data class NutritionEntity(
    val calories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0
)