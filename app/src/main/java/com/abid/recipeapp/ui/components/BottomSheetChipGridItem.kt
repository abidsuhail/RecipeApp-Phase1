package com.abid.recipeapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Dims

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> BottomSheetChipGridItem(
    title: String,
    items: List<T>,
    selectedLabel: String?,
    labelOf: (T) -> String,
    onChipClicked: (String) -> Unit
) {
    Column {
        Text(title, fontSize = Dims.Sp20, fontWeight = FontWeight.SemiBold)
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dims.Dp12),
            horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
            verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
        ) {
            items.forEach { item ->
                val label = labelOf(item)
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (label == selectedLabel) Orange40 else Color.Unspecified
                    ),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { onChipClicked(label) }
                    )
                ) {
                    Text(label, modifier = Modifier.padding(Dims.Dp10))
                }
            }
        }
    }
}