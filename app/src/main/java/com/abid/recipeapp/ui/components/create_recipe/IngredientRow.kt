package com.abid.recipeapp.ui.components.create_recipe

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abid.recipeapp.data.model.IngredientModel

@Composable
fun IngredientRow(
    ingredientModel: IngredientModel,
    onIngredientNameChange: (String) -> Unit,
    onIngredientAmountChange: (String) -> Unit,
    onIngredientRemove: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = ingredientModel.item,
            onValueChange = onIngredientNameChange,
            label = { Text("Ingredient name") },
            isError = ingredientModel.item.isBlank(),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )
        OutlinedTextField(
            value = ingredientModel.amount,
            onValueChange = onIngredientAmountChange,
            label = { Text("Amount") },
            isError = ingredientModel.amount.isBlank(),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)

        )
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close icon",
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current,
                    onClick = {
                        onIngredientRemove()
                    }
                )
        )
    }
}