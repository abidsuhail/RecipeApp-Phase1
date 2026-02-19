package com.abid.recipeapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abid.recipeapp.data.model.RecipeModel

@Composable
fun RecipeCard(recipe: RecipeModel){
    Card(modifier = Modifier.fillMaxWidth().padding(10.dp),
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
            Text(recipe.title, fontWeight = FontWeight.SemiBold)
        }
    }
}