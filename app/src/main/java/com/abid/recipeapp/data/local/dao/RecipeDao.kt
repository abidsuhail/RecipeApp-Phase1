package com.abid.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)


    @Query("SELECT * FROM recipes ORDER BY id ASC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: String): RecipeEntity?

    @Query(
        """
    SELECT * FROM recipes
    WHERE (:categoryName IS NULL OR category = :categoryName)
    AND (:cuisineName IS NULL OR cuisine = :cuisineName)
    AND (:diffLvl IS NULL OR difficulty = :diffLvl)
    AND (:title IS NULL OR title LIKE '%' || :title || '%')
    ORDER BY id ASC
    """
    )
    fun getRecipesByFilters(
        categoryName: String?,
        cuisineName: String?,
        diffLvl: String?,
        title: String?
    ): Flow<List<RecipeEntity>>

    @Query("UPDATE recipes SET isDataSynced = 1 WHERE id = :recipeId")
    suspend fun markAsSynced(recipeId: String)

    @Query("SELECT * FROM recipes WHERE isDataSynced = 0")
    suspend fun getUnsyncedRecipes(): List<RecipeEntity>

    @Query("UPDATE recipes SET isDataSynced = 0 WHERE id = :recipeId")
    suspend fun markAsUnsynced(recipeId: String)

    @Query("UPDATE recipes SET image = :imageUrl WHERE id = :recipeId")
    suspend fun updateRecipeImage(recipeId: String, imageUrl: String)

    @Query("DELETE FROM recipes WHERE isDataSynced = 1 AND id NOT IN (:remoteIds)")
    suspend fun deleteStaleSyncedRecipes(remoteIds: List<String>)

}