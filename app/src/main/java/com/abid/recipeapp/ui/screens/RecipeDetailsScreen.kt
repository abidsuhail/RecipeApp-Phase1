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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.components.BackButton
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.ui.theme.LocalAppCustomColors
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Constants.CALORIES
import com.abid.recipeapp.util.Constants.CARBS
import com.abid.recipeapp.util.Constants.FAT
import com.abid.recipeapp.util.Constants.FAVORITE_CONTENT_DESC
import com.abid.recipeapp.util.Constants.NUTRITION_PER_SERVING_
import com.abid.recipeapp.util.Constants.PROTEIN
import com.abid.recipeapp.util.Constants.getServingsAndUrList
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.viewmodel.RecipeDetailsViewModel
import kotlinx.coroutines.delay


private const val RECIPE_IMAGE = "Recipe image"

@Composable
fun RecipeDetailsScreen(
    recipeId: String?,
    onClickBackBtn: () -> Unit,
    vm: RecipeDetailsViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.observeAsState()
    LaunchedEffect(recipeId) {
        vm.loadRecipeById(recipeId)
        vm.refreshIngredientsTabs()
        vm.fetchIsInFav(recipeId)
    }
    LaunchedEffect(uiState?.recipeErrorMsg) {
        if (uiState?.recipeErrorMsg != null) {
            delay(1000)
            onClickBackBtn()
        }
    }
    if (uiState?.isRecipeLoading == true) {
        LoadingStateBox()
    }
    if (uiState?.recipeErrorMsg != null) {
        ErrorStateBox(uiState?.recipeErrorMsg!!)
    }
    if (uiState?.recipe != null) {
        var isFavorite by remember { mutableStateOf(false) }
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = Orange40,
                    onClick = {
                        isFavorite = !isFavorite
                        vm.toggleFav(recipeId!!)
                    }
                ) {
                    Icon(
                        imageVector = if (uiState?.isFav == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = FAVORITE_CONTENT_DESC,
                    )
                }
            },
        ) { innerPadding ->
            Box(
                Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(Dims.Dp300),
                ) {
                    AsyncImage(
                        model = uiState?.recipe!!.image,
                        contentDescription = RECIPE_IMAGE,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dims.Dp300),
                        contentScale = ContentScale.Crop
                    )
                    BackButton {
                        onClickBackBtn()
                    }
                }

                RecipeDetailsCard(uiState?.recipe!!, uiState?.ingredientsTabList!!)
            }
        }
    }
}

@Composable
private fun RecipeDetailsCard(
    recipe: RecipeModel,
    ingredientsTabModelList: List<IngredientsTabModel>
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dims.Dp270),
        colors = CardDefaults.cardColors(LocalAppCustomColors.current.recipeDetailsClippedCardBg),
        shape = RoundedCornerShape(Dims.Dp30)
    ) {
        Column(Modifier.padding(Dims.Dp20)) {
            Text(recipe.title, fontSize = Dims.Sp25)
            Text("by ${recipe.author}", fontSize = Dims.Sp12)
            ServingsAndDurationRow(recipe)
            Text(recipe.description, modifier = Modifier.padding(vertical = Dims.Dp10))
            MacroCard(recipe)
            RecipeDetailsBottomTab(recipe, ingredientsTabModelList)

        }
    }
}


@Composable
private fun MacroCard(recipe: RecipeModel) {
    Card(
        Modifier
            .fillMaxWidth(),
    ) {
        Box(Modifier.padding(Dims.Dp10)) {
            Column {
                Text(NUTRITION_PER_SERVING_)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dims.Dp15),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MacroItemColumn(CALORIES, recipe.nutrition.calories, Orange40)
                    MacroItemColumn(PROTEIN, recipe.nutrition.protein, Color.Green)
                    MacroItemColumn(CARBS, recipe.nutrition.carbs, Color.Yellow)
                    MacroItemColumn(FAT, recipe.nutrition.fat, Color.Red)
                }
            }
        }
    }
}

@Composable
private fun MacroItemColumn(label: String, value: Int, valueColor: Color = Color.DarkGray) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(value.toString(), fontSize = Dims.Sp25, color = valueColor)
        Text(label)
    }
}

@Composable
private fun ServingsAndDurationRow(recipe: RecipeModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dims.Dp10),
        horizontalArrangement = Arrangement.spacedBy(Dims.Dp8)
    ) {
        getServingsAndUrList(recipe).forEach {
            Card(
                Modifier
                    .weight(Dims.W1)
                    .height(Dims.Dp80),
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
                        fontSize = Dims.Sp12,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecipeDetailsBottomTab(
    recipe: RecipeModel,
    ingredientsTabModelList: List<IngredientsTabModel>,
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.padding(vertical = Dims.Dp10)) {
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            ingredientsTabModelList.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title.label) }
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
                    .padding(vertical = Dims.Dp5)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dims.Dp15),
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
            Box(Modifier.padding(vertical = Dims.Dp10)) {
                Row {
                    Text("*")
                    Text(instruction)
                }
            }
        }
    }

}