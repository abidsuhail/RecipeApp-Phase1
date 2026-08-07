package com.abid.recipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abid.recipeapp.data.local.dao.CuisinesDao
import com.abid.recipeapp.data.local.dao.RecipeDao
import com.abid.recipeapp.data.local.entities.RecipeCuisinesEntity
import com.abid.recipeapp.data.local.entities.RecipeEntity

@Database(
    entities = [RecipeEntity::class, RecipeCuisinesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun cuisinesDao(): CuisinesDao

}