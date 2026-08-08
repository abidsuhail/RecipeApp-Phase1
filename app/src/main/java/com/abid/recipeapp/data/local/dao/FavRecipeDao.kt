package com.abid.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.FavRecipeEntity

@Dao
interface FavRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fav: FavRecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favs: List<FavRecipeEntity>)

    @Query("DELETE FROM fav_recipes")
    suspend fun deleteAll()

    @Query("SELECT recipeId FROM fav_recipes")
    suspend fun getAllFavRecipeIds(): List<String>

    @Query("SELECT * FROM fav_recipes WHERE recipeId = :recipeId LIMIT 1")
    suspend fun isFav(recipeId: String): FavRecipeEntity?

    @Delete
    suspend fun delete(fav: FavRecipeEntity)
}