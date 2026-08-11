package com.abid.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.FavRecipeEntity
import com.abid.recipeapp.data.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fav: FavRecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favs: List<FavRecipeEntity>)

    @Query("DELETE FROM fav_recipes")
    suspend fun deleteAll()

    @Query(
        """
        SELECT recipes.* FROM recipes
        INNER JOIN fav_recipes ON recipes.id = fav_recipes.recipeId
        ORDER BY recipes.id ASC
    """
    )
    fun getAllFavRecipesFlow(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM fav_recipes WHERE recipeId = :recipeId LIMIT 1")
    suspend fun isFav(recipeId: String): FavRecipeEntity?

    @Delete
    suspend fun delete(fav: FavRecipeEntity): Int
}