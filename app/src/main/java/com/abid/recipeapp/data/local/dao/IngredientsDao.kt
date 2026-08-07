package com.abid.recipeapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abid.recipeapp.data.local.entities.IngredientEntity

@Dao
interface IngredientsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: IngredientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<IngredientEntity>)

    @Query("SELECT * FROM ingredients ORDER BY id ASC")
    fun getAllIngredients(): LiveData<List<IngredientEntity>>


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