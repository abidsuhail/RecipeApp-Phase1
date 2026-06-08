package com.abid.recipeapp.ui.components.create_recipe

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.abid.recipeapp.data.model.IngredientFormStateModel
import com.abid.recipeapp.util.Constants.AMOUNT
import com.abid.recipeapp.util.Constants.CLOSE_ICON
import com.abid.recipeapp.util.Constants.INGREDIENT_NAME
import com.abid.recipeapp.util.Dims


@Composable
fun IngredientRow(
    ingredientModel: IngredientFormStateModel,
    onIngredientNameChange: (String) -> Unit,
    onIngredientAmountChange: (String) -> Unit,
    onIngredientRemove: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = ingredientModel.item,
            onValueChange = onIngredientNameChange,
            label = { Text(INGREDIENT_NAME) },
            isError = ingredientModel.item.isBlank(),
            modifier = Modifier
                .weight(1f)
                .padding(Dims.Dp5)
        )
        OutlinedTextField(
            value = ingredientModel.amount,
            onValueChange = onIngredientAmountChange,
            label = { Text(AMOUNT) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = ingredientModel.amount.isBlank(),
            modifier = Modifier
                .weight(1f)
                .padding(Dims.Dp5)

        )
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = CLOSE_ICON,
            modifier = Modifier
                .padding(horizontal = Dims.Dp5)
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