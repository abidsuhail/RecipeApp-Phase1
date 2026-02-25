package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.ui.components.RecipeCard
import com.abid.recipeapp.ui.navigation.BottomNavBar
import com.abid.recipeapp.ui.state.RecipeListUiState
import com.abid.recipeapp.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(currentRoute: String?, onClickRecipeItem: (RecipeModel) -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavBar(currentRoute) {} }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                RecipeListHeader()
                RecipeList(onClickRecipeItem)
            }
        }
    }
}

@Composable
private fun RecipeListHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        Text("Discover Recipes", fontSize = 30.sp)
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Add button",
            modifier = Modifier.size(50.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun RecipeList(
    onClickRecipeItem: (RecipeModel) -> Unit,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    val allRecipes by recipeViewModel.allRecipes.observeAsState()
    LaunchedEffect(Unit) {
        recipeViewModel.loadAllRecipes()
    }
    when (allRecipes) {
        is RecipeListUiState.Loading -> {
            LoadingStateBox()
        }

        is RecipeListUiState.Error -> {
            ErrorStateBox("No recipes found!")
        }

        is RecipeListUiState.Success -> {
            val state = (allRecipes as RecipeListUiState.Success)
            val recipes = state.recipes
            LazyColumn {
                items(items = recipes, key = { recipe -> recipe.id }) { recipe ->
                    Box(
                        modifier = Modifier.clickable(
                            onClick = {
                                onClickRecipeItem(recipe)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = LocalIndication.current,
                        )
                    ) {
                        RecipeCard(recipe)
                    }
                }
            }
        }

        else -> {
            ErrorStateBox("Something went wrong!")

        }
    }
}