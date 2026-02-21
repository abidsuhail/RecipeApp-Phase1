package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.theme.Orange40

@Composable
fun RecipeDetailsScreen(navController: NavHostController, recipe: RecipeModel) {
    Scaffold { innerPadding ->
        Box(
            Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = recipe.image,
                contentDescription = "Recipe image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            RecipeDetailsCard(recipe)
        }
    }
}

@Composable
private fun RecipeDetailsCard(recipe: RecipeModel) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 270.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(recipe.title, fontSize = 25.sp)
            Text("by ${recipe.author}", fontSize = 12.sp)
            ServingsAndDurationRow(recipe)
            Text(recipe.description, modifier = Modifier.padding(vertical = 10.dp))
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = CardDefaults.cardColors(Color.LightGray)
            ) {

                // Text("Nutrition (per serving)")
                Text("TODO")
            }
            BottomTab(recipe)

        }
    }
}

@Composable
private fun ServingsAndDurationRow(recipe: RecipeModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            "${recipe.cookTime + recipe.prepTime} min",
            "${recipe.servings} servings",
            recipe.difficulty
        ).forEach {
            Card(
                Modifier
                    .weight(1f)
                    .height(80.dp),
                colors = CardDefaults.cardColors(Orange40)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "")
                    Text(
                        it.toString(),
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomTab(recipe: RecipeModel) {
    val tabs = listOf("Ingredients", "Instructions")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> IngredientsFragment(recipe)
            1 -> InstructionsFragment(recipe)
        }
    }
}

@Composable
private fun IngredientsFragment(recipe: RecipeModel) {
    Column {
        recipe.ingredients.forEach { ingredient ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(ingredient.item)
                    Text(ingredient.amount)
                }
            }
        }
    }

}

@Composable
private fun InstructionsFragment(recipe: RecipeModel) {
    Column {
        recipe.instructions.forEach { instruction ->
            Box(Modifier.padding(vertical = 10.dp)) {
                Row {
                    Text("*")
                    Text(instruction)
                }
            }
        }
    }

}