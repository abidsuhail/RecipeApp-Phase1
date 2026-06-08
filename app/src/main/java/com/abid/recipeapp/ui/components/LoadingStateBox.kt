package com.abid.recipeapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Dims

@Composable
fun LoadingStateBox() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Orange40,
            strokeWidth = Dims.Dp4
        )
    }
}