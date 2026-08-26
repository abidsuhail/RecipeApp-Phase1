package com.abid.recipeapp.util

fun String.isLocalUri(): Boolean {
    return startsWith("content://") || startsWith("file://") || !startsWith("http")
}