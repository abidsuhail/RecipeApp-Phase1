package com.abid.recipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abid.recipeapp.data.local.entities.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}