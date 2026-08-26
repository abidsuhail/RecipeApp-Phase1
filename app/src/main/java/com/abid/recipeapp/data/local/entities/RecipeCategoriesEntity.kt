package com.abid.recipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class RecipeCategoriesEntity(
    @PrimaryKey val id: String = "",
    val label: String = ""
)