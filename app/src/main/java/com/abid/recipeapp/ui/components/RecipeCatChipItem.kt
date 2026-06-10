package com.abid.recipeapp.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Dims

@Composable
fun RecipeCatChipItem(
    recipeCat: RecipeCategoriesDataModel,
    isSelected: Boolean,
    onClickRecipeCat: (RecipeCategoriesDataModel) -> Unit
) {

    val bagColor = if (isSelected) {
        Orange40
    } else {
        Color(0xFFE0E0E0)
    }
    val textColor = if (isSelected) {
        Color.White
    } else {
        Color.DarkGray
    }
    Box(
        modifier = Modifier
            .defaultMinSize(minWidth = Dims.Dp100)
            .padding(Dims.Dp5)
            .clip(RoundedCornerShape(Dims.Dp50))
            .background(bagColor)
            .clickable(
                onClick = {
                    onClickRecipeCat(recipeCat)
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
            )
    ) {
        Text(
            recipeCat.label,
            color = textColor,
            modifier = Modifier
                .padding(Dims.Dp8)
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
        )
    }
}