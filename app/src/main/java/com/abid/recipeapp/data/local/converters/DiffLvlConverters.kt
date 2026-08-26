package com.abid.recipeapp.data.local.converters

import androidx.room.TypeConverter
import com.abid.recipeapp.data.model.RecipeDiffLvl


class DiffLvlConverters {
    @TypeConverter
    fun fromDiffLvl(value: RecipeDiffLvl): String = value.name

    @TypeConverter
    fun toDiffLvl(value: String): RecipeDiffLvl = RecipeDiffLvl.valueOf(value)
}