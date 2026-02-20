package com.abid.recipeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.abid.recipeapp.data.model.RecipeModel

@Composable
fun RecipeCard(recipe: RecipeModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        elevation = CardDefaults.cardElevation()) {
        AsyncImage(
            model = recipe.image,
            contentDescription = "Recipe image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.padding(10.dp)){
            Column {
                Text(recipe.title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                Text(recipe.description, fontSize = 13.sp)
                RecipeCardBottomRow(recipe)
        }
    }
}
}

@Composable
private fun RecipeCardBottomRow(recipe: RecipeModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Icon(imageVector = Icons.Filled.Info, contentDescription = "")
            Text(
                "${(recipe.cookTime + recipe.prepTime)}min",
                modifier = Modifier.padding(horizontal = 4.dp),
                fontSize = 14.sp
            )
            Icon(imageVector = Icons.Filled.Face, contentDescription = "")
            Text(
                recipe.servings.toString(),
                modifier = Modifier.padding(horizontal = 4.dp),
                fontSize = 14.sp
            )
        }
        Text(recipe.difficulty,fontSize = 14.sp)
    }
}