package com.abid.recipeapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abid.recipeapp.data.local.dao.CategoriesDao
import com.abid.recipeapp.data.local.dao.CuisinesDao
import com.abid.recipeapp.data.local.dao.DiffLvlDao
import com.abid.recipeapp.data.local.dao.RecipeDao
import com.abid.recipeapp.data.local.entities.RecipeCategoriesEntity
import com.abid.recipeapp.data.local.entities.RecipeCuisinesEntity
import com.abid.recipeapp.data.local.entities.RecipeDiffLvlEntity
import com.abid.recipeapp.data.local.entities.RecipeEntity

@Database(
    entities = [RecipeEntity::class, RecipeCuisinesEntity::class, RecipeCategoriesEntity::class, RecipeDiffLvlEntity::class],
    version = 2,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun cuisinesDao(): CuisinesDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun diffLvlDao(): DiffLvlDao
}