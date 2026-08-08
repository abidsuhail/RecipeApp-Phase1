package com.abid.recipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients_tabs")
data class IngredientsTabEntity(
    @PrimaryKey val id: String = "",
    val label: String = ""
)