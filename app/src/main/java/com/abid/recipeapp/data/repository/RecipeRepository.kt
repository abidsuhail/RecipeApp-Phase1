package com.abid.recipeapp.data.repository

import com.abid.recipeapp.util.network.NetworkMonitor
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val networkMonitor: NetworkMonitor
) {
}