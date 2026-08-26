package com.abid.recipeapp.data.local.converters

import androidx.room.TypeConverter
import com.abid.recipeapp.data.local.entities.IngredientEntity
import com.abid.recipeapp.data.model.RecipeDiffLvl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeConverters {
    private val gson = Gson()

    // ---- List<String> (tags, instructions) ----
    @TypeConverter
    fun fromStringList(list: List<String>?): String =
        gson.toJson(list ?: emptyList<String>())

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    // ---- List<IngredientEntity> ----
    @TypeConverter
    fun fromIngredientList(list: List<IngredientEntity>?): String =
        gson.toJson(list ?: emptyList<IngredientEntity>())

    @TypeConverter
    fun toIngredientList(json: String): List<IngredientEntity> {
        val type = object : TypeToken<List<IngredientEntity>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }


    // ---- Enum ----
    @TypeConverter
    fun fromDifficulty(value: RecipeDiffLvl): String = value.name

    @TypeConverter
    fun toDifficulty(value: String): RecipeDiffLvl = RecipeDiffLvl.valueOf(value)
}