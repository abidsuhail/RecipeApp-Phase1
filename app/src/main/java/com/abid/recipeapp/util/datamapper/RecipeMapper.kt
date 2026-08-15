package com.abid.recipeapp.util.datamapper

import com.abid.recipeapp.data.local.entities.IngredientEntity
import com.abid.recipeapp.data.local.entities.NutritionEntity
import com.abid.recipeapp.data.local.entities.RecipeEntity

import com.abid.recipeapp.data.model.IngredientModel
import com.abid.recipeapp.data.model.NutritionModel
import com.abid.recipeapp.data.model.RecipeModel

fun RecipeModel.toEntity(isDataSynced: Boolean): RecipeEntity = RecipeEntity(
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
    ingredients = ingredients.map { it.toEntity() },
    instructions = instructions,
    nutrition = nutrition.toEntity(),
    isDataSynced = isDataSynced
)

fun RecipeEntity.toModel(): RecipeModel = RecipeModel(
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
    ingredients = ingredients.map { it.toModel() },
    instructions = instructions,
    nutrition = nutrition.toModel()
)

fun IngredientModel.toEntity() = IngredientEntity(item, amount)
fun IngredientEntity.toModel() = IngredientModel(item, amount)

fun NutritionModel.toEntity() = NutritionEntity(calories, protein, carbs, fat)
fun NutritionEntity.toModel() = NutritionModel(calories, protein, carbs, fat)