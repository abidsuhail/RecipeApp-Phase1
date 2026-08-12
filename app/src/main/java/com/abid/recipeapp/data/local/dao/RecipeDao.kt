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


    /*    @Query("SELECT * FROM recipes ORDER BY id ASC")
        suspend fun getAllRecipes(): List<RecipeEntity>*/

    @Query("SELECT * FROM recipes ORDER BY id ASC")
    fun getAllRecipesFlow(): Flow<List<RecipeEntity>>

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

    @Query("DELETE FROM recipes")
    suspend fun deleteAll()
}