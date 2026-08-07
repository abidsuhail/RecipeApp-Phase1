package com.abid.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.RecipeCategoriesEntity

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: RecipeCategoriesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<RecipeCategoriesEntity>)

    @Query("SELECT * FROM categories ORDER BY id ASC")
    suspend fun getAllCategories(): List<RecipeCategoriesEntity>

}