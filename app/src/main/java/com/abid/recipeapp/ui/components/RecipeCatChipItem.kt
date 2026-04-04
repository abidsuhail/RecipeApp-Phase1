package com.abid.recipeapp.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.ui.theme.Orange40

@Composable
fun RecipeCatChipItem(
    recipeCat: RecipeCategoriesDataModel,
    onClickRecipeCat: (RecipeCategoriesDataModel) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Orange40)
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
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}