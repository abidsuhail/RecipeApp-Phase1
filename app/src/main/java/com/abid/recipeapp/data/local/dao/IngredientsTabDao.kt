package com.abid.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.IngredientsTabEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientsTabDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tab: IngredientsTabEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tabs: List<IngredientsTabEntity>)

    @Query("SELECT * FROM ingredients_tabs ORDER BY id ASC")
    fun getAllIngredientsTabs(): Flow<List<IngredientsTabEntity>>
}