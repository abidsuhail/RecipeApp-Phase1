package com.abid.recipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abid.recipeapp.data.model.RecipeDiffLvl

@Entity(tableName = "difficulty_levels")
data class RecipeDiffLvlEntity(
    @PrimaryKey val id: String = "",
    val label: RecipeDiffLvl = RecipeDiffLvl.EASY
)