package com.abid.recipeapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.RecipeCuisinesEntity

@Dao
interface CuisinesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: RecipeCuisinesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<RecipeCuisinesEntity>)

    @Query("SELECT * FROM cuisines ORDER BY id ASC")
    fun getAllCuisines(): LiveData<List<RecipeCuisinesEntity>>


    /*
        @Update
        suspend fun update(recipe: RecipeEntity)

        @Delete
        suspend fun delete(recipe: RecipeEntity)

        @Query("SELECT * FROM recipes ORDER BY id ASC")
        fun getAllRecipes(): LiveData<List<RecipeEntity>>

        @Query("SELECT * FROM recipes WHERE id = :id")
        suspend fun getRecipeById(id: Int): RecipeEntity?

        @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%' ORDER BY id ASC")
        fun searchRecipesByTitle(query: String): LiveData<List<RecipeEntity>>

        @Query("DELETE FROM recipes")
        suspend fun deleteAll()*/
}