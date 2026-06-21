package com.abid.recipeapp.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Constants.BACK_IMG
import com.abid.recipeapp.util.Dims


@Composable
fun BackButton(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = BACK_IMG,
        tint = Color.White,
        modifier = Modifier
            .padding(top = Dims.Dp50, start = Dims.Dp20)
            .size(Dims.Dp40)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            )
            .background(Orange40, RoundedCornerShape(Dims.Dp100))

    )
}