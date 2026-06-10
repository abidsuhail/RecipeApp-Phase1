package com.abid.recipeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.theme.getDifficultyLvlBgColor
import com.abid.recipeapp.util.Constants.FACE_ICON
import com.abid.recipeapp.util.Constants.INFO_ICON
import com.abid.recipeapp.util.Constants.RECIPE_IMAGE
import com.abid.recipeapp.util.Constants.getTotalCookTimeInMin
import com.abid.recipeapp.util.Dims


@Composable
fun RecipeCard(recipe: RecipeModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dims.Dp10),
        elevation = CardDefaults.cardElevation()
    ) {
        Column {
            AsyncImage(
                model = recipe.image,
                contentDescription = RECIPE_IMAGE,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dims.Dp200),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.padding(Dims.Dp10)) {
                Column {
                    Text(recipe.title, fontWeight = FontWeight.SemiBold, fontSize = Dims.Sp20)
                    Text(recipe.description, fontSize = Dims.Sp13)
                    RecipeCardBottomRow(recipe)
                }
            }
        }
    }
}


@Composable
private fun RecipeCardBottomRow(recipe: RecipeModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dims.Dp10),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Icon(imageVector = Icons.Filled.Info, contentDescription = INFO_ICON)
            Text(
                getTotalCookTimeInMin(recipe),
                modifier = Modifier.padding(horizontal = Dims.Dp4),
                fontSize = Dims.Sp14
            )
            Icon(imageVector = Icons.Filled.Face, contentDescription = FACE_ICON)
            Text(
                recipe.servings.toString(),
                modifier = Modifier.padding(horizontal = Dims.Dp4),
                fontSize = Dims.Sp14
            )
        }
        Card(
            colors = CardDefaults.cardColors(getDifficultyLvlBgColor(recipe.difficulty)),
            shape = RoundedCornerShape(Dims.Dp5)
        ) {
            Text(
                recipe.difficulty.toString(),
                fontSize = Dims.Sp10,
                modifier = Modifier.padding(horizontal = Dims.Dp5)
            )
        }
    }
}

