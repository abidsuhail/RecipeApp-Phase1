package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abid.recipeapp.util.newRecipeCategories
import com.abid.recipeapp.util.newRecipeCuisine
import com.abid.recipeapp.util.newRecipeDifficultyLvl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    var desc by rememberSaveable { mutableStateOf("") }
    var descError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TopBar()
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(12.dp)
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = if (it.isBlank()) "Recipe Title cannot be empty" else null
                    },
                    label = { Text("Recipe Title") },
                    isError = nameError != null,
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = {
                        desc = it
                        descError = if (it.isBlank()) "Description cannot be empty" else null
                    },
                    label = { Text("Description") },
                    isError = descError != null,
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth(),
                    singleLine = false,
                    maxLines = 5
                )
                Row {
                    OutlinedTextField(
                        value = desc,
                        onValueChange = {
                            desc = it
                            descError = if (it.isBlank()) "Description cannot be empty" else null
                        },
                        label = { Text("Prep Time(min)") },
                        isError = descError != null,
                        modifier = Modifier
                            .weight(1f),
                    )
                    Spacer(Modifier.width(5.dp))
                    OutlinedTextField(
                        value = desc,
                        onValueChange = {
                            desc = it
                            descError = if (it.isBlank()) "Description cannot be empty" else null
                        },
                        label = { Text("Cook Time(min)") },
                        isError = descError != null,
                        modifier = Modifier
                            .weight(1f),
                    )
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = if (it.isBlank()) "Recipe Title cannot be empty" else null
                    },
                    label = { Text("Servings") },
                    isError = nameError != null,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(15.dp))
                Text("Category")
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    newRecipeCategories.forEach { item ->
                        Card() {
                            Text(item, modifier = Modifier.padding(10.dp))
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))
                Text("Cuisine")
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    newRecipeCuisine.forEach { item ->
                        Card() {
                            Text(item, modifier = Modifier.padding(10.dp))
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))
                Text("Difficulty")
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    newRecipeDifficultyLvl.forEach { item ->
                        Card() {
                            Text(item.name, modifier = Modifier.padding(10.dp))
                        }
                    }
                }

            }
        }
    }
}

/*@Composable
private fun RecipeTextField(
    label: String,
    value: String,
    isError: Boolean,
    isSingleLine: Boolean = true,
    onValueChange: (String, String?) -> Unit
) {
    val height = if (isSingleLine) 60.dp else 150.dp
    Text(label)
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it, if (it.isBlank()) "Name cannot be empty" else null)
        },
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        singleLine = isSingleLine,
        maxLines = if (isSingleLine) 5 else 1
    )
}*/

@Composable
private fun TopBar() {
    Box(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back button"
            )
            Text("Create Recipe")
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "Back button"
            )
        }
    }
}