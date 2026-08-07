package com.abid.recipeapp.data.local.entities

import androidx.room.Entity

@Entity(tableName = "cuisines")
data class RecipeCuisinesEntity(
    val id: String = "",
    val label: String = ""
)