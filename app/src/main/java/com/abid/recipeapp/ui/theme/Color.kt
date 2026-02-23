package com.abid.recipeapp.ui.theme

import androidx.compose.ui.graphics.Color
import com.abid.recipeapp.util.RecipeDiffLvl

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Orange40 = Color(0xFFff8c00)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


fun getDifficultyLvlBgColor(difficultyLvl: RecipeDiffLvl): Color {
    return when (difficultyLvl) {
        RecipeDiffLvl.EASY -> {
            Color(0xFF81C784)
        }

        RecipeDiffLvl.MEDIUM -> {
            Color(0xFFFFB74D)
        }

        RecipeDiffLvl.HARD -> {
            Color(0xFFE57373)
        }

        else -> {
            Color.Gray
        }
    }
}