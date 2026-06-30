package com.abid.recipeapp.ui.theme

import androidx.compose.ui.graphics.Color
import com.abid.recipeapp.data.model.RecipeDiffLvl

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Orange40 = Color(0xFFff8c00)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val LightAppColors = AppCustomColors(
    settingsThemeModeGrid = Color.LightGray,
    settingsCardColor = Color(0xFFE0E0E0),
    homeBottomNavColor = Color.White,
    recipeDetailsClippedCardBg = Color.White,
    searchCardBg = Color(0xFFE0E0E0),
    searchApplyFilterResetBtnBg = Color.Gray,
    searchApplyFilterApplyBtnBg = Orange40,
    searchResetFilterTxtColor = Color.Unspecified
)

val DarkAppColors = AppCustomColors(
    settingsThemeModeGrid = Color.Black,
    settingsCardColor = Color.Black,
    homeBottomNavColor = Color.Black,
    recipeDetailsClippedCardBg = Color.Black,
    searchCardBg = Color.Black,
    searchApplyFilterResetBtnBg = Color.Gray,
    searchApplyFilterApplyBtnBg = Orange40,
    searchResetFilterTxtColor = Color.White
)

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