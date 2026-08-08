package com.abid.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.RecipeDiffLvlEntity

@Dao
interface DiffLvlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diffLvl: RecipeDiffLvlEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(diffLvls: List<RecipeDiffLvlEntity>)

    @Query("SELECT * FROM difficulty_levels ORDER BY id ASC")
    suspend fun getAllDiffLvls(): List<RecipeDiffLvlEntity>
}