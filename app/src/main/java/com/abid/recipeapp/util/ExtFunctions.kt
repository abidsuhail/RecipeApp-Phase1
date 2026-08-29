package com.abid.recipeapp.util

import com.abid.recipeapp.data.model.RecipeDiffLvl

fun String.isLocalUri(): Boolean {
    return startsWith("content://") || startsWith("file://") || !startsWith("http")
}

fun String.toRecipeDiffLvl(): RecipeDiffLvl {
    return runCatching { RecipeDiffLvl.valueOf(this.trim().uppercase()) }.getOrNull()
        ?: RecipeDiffLvl.EASY
}