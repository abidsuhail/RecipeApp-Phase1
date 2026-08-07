package com.abid.recipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cuisines")
data class RecipeCuisinesEntity(
    @PrimaryKey val id: String = "",
    val label: String = ""
)