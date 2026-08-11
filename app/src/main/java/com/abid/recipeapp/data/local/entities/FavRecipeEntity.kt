package com.abid.recipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_recipes")
data class FavRecipeEntity(
    @PrimaryKey val recipeId: String = "",
    val isSynced: Boolean = false
)