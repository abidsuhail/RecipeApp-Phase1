package com.abid.recipeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abid.recipeapp.data.local.entities.RecipeEntity
import com.abid.recipeapp.util.Constants

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    Constants.RECIPE_DB_NAME
                )
                    .fallbackToDestructiveMigration() // i need to remove in production, use proper migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}