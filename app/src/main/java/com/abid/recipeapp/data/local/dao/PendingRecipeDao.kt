package com.abid.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.CreateRecipeFormStateEntity

@Dao
interface PendingRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: CreateRecipeFormStateEntity)

    @Query("SELECT * FROM pending_recipes WHERE isSynced = 0")
    suspend fun getUnsyncedRecipes(): List<CreateRecipeFormStateEntity>

    @Query("DELETE FROM pending_recipes WHERE id = :id")
    suspend fun delete(id: String)

    @Query("UPDATE pending_recipes SET image = :url, isImageUploaded = 1 WHERE id = :id")
    suspend fun markImageUploaded(id: String, url: String)

    @Query("UPDATE pending_recipes SET image = :remoteUrl WHERE id = :recipeId")
    suspend fun updateRemoteImageUrl(recipeId: String, remoteUrl: String)
}