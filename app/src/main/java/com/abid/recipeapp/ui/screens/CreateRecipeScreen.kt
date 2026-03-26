package com.abid.recipeapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.ui.components.create_recipe.CreateRecipeTopBar
import com.abid.recipeapp.ui.components.create_recipe.ImagePicker
import com.abid.recipeapp.ui.components.create_recipe.IngredientRow
import com.abid.recipeapp.ui.components.create_recipe.InstructionRow
import com.abid.recipeapp.ui.state.RecipeSaveState
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.viewmodel.CreateRecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(
    vm: CreateRecipeViewModel = hiltViewModel(),
    onClickBackBtn: () -> Unit,
    handleGotoHomeScreen: () -> Unit
) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var isFirstRender by remember { mutableStateOf(true) }

    var recipeNameError by remember { mutableStateOf<String?>(null) }
    var descError by remember { mutableStateOf<String?>(null) }
    var prepTimeError by remember { mutableStateOf<String?>(null) }
    var cookTimeMinError by remember { mutableStateOf<String?>(null) }
    var servingsError by remember { mutableStateOf<String?>(null) }
    var nutritionCaloriesError by remember { mutableStateOf<String?>(null) }
    var nutritionProteinError by remember { mutableStateOf<String?>(null) }
    var nutritionCarbsError by remember { mutableStateOf<String?>(null) }
    var nutritionFatsError by remember { mutableStateOf<String?>(null) }
    var uploadedImageUri by remember { mutableStateOf<Uri?>(null) }

    val uiState by vm.uiState.observeAsState()
    LaunchedEffect(Unit) {
        vm.loadRecipeCategories()
        vm.loadCuisines()
        vm.loadRecipeDifficulties()
    }
    LaunchedEffect(uiState!!.recipeModel.instructions.size + uiState!!.recipeModel.ingredients.size) {
        if (isFirstRender) {
            isFirstRender = false
            return@LaunchedEffect
        }
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    CreateRecipeTopBar(onClickBackBtn = onClickBackBtn, onClickSubmitBtn = {
                        vm.saveRecipe(
                            uiState!!.recipeModel,
                            uploadedImageUri
                        )
                    })
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(12.dp)
        ) {
            if (uiState?.isLoading == true) {
                LoadingStateBox()
            }
            if (uiState?.errorMsg != null) {
                ErrorStateBox(uiState?.errorMsg!!)
            }
            if (uiState?.recipeSaveState is RecipeSaveState.Success) {
                handleGotoHomeScreen()
            }

            Column(Modifier.verticalScroll(scrollState)) {
                ImagePicker { uri ->
                    uploadedImageUri = uri
                }
                OutlinedTextField(
                    value = uiState!!.recipeModel.title,
                    onValueChange = {
                        vm.onTitleChange(it)
                        recipeNameError = if (it.isBlank()) "Recipe Title cannot be empty" else null
                    },
                    label = { Text("Recipe Title") },
                    isError = recipeNameError != null,
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = uiState!!.recipeModel.description,
                    onValueChange = {
                        vm.onDescriptionChange(it)
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
                        value = uiState!!.recipeModel.prepTime.toString(),
                        onValueChange = {
                            vm.onPrepTimeChange(it)
                            prepTimeError =
                                if (it.isBlank()) "Description cannot be empty" else null
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text("Prep Time(min)") },
                        isError = prepTimeError != null,
                        modifier = Modifier
                            .weight(1f),
                    )
                    Spacer(Modifier.width(5.dp))
                    OutlinedTextField(
                        value = uiState!!.recipeModel.cookTime.toString(),
                        onValueChange = {
                            vm.onCookTimeChange(it)
                            cookTimeMinError =
                                if (it.isBlank()) "Description cannot be empty" else null
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text("Cook Time(min)") },
                        isError = cookTimeMinError != null,
                        modifier = Modifier
                            .weight(1f),
                    )
                }
                OutlinedTextField(
                    value = uiState!!.recipeModel.servings.toString(),
                    onValueChange = {
                        vm.onServingsChange(it)
                        servingsError = if (it.isBlank()) "Recipe Title cannot be empty" else null
                    },
                    label = { Text("Servings") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = servingsError != null,
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
                    uiState?.categoriesList?.forEach { item ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (item.label == uiState!!.recipeModel.category) {
                                    Orange40
                                } else {
                                    Color.Unspecified
                                }
                            ),
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    vm.onCategoryChange(item.label)
                                }
                            )
                        ) {
                            Text(item.label, modifier = Modifier.padding(10.dp))
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
                    uiState?.cuisinesList?.forEach { item ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (item.label == uiState!!.recipeModel.cuisine) {
                                    Orange40
                                } else {
                                    Color.Unspecified
                                }
                            ),
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    vm.onCuisineChange(item.label)

                                }
                            )
                        ) {
                            Text(item.label, modifier = Modifier.padding(10.dp))
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
                    uiState?.difficultiesList?.forEach { item ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (item.label.name == uiState!!.recipeModel.difficulty.name) {
                                    Orange40
                                } else {
                                    Color.Unspecified
                                }
                            ),
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    vm.onDifficultyChange(item.label)
                                }
                            )
                        ) {
                            Text(item.label.name, modifier = Modifier.padding(10.dp))
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))
                Text("Nutrition")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = uiState!!.recipeModel.nutrition.calories.toString(),
                        onValueChange = {
                            vm.onCaloriesChange(it)
                            nutritionCaloriesError =
                                if (it.isBlank()) "Please enter the calories" else null
                        },
                        label = { Text("Calories") },
                        isError = nutritionCaloriesError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    )
                    OutlinedTextField(
                        value = uiState!!.recipeModel.nutrition.protein.toString(),
                        onValueChange = {
                            vm.onProteinChange(it)
                            nutritionProteinError =
                                if (it.isBlank()) "Please enter the protein" else null
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text("Protein") },
                        isError = nutritionProteinError != null,
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = uiState!!.recipeModel.nutrition.carbs.toString(),
                        onValueChange = {
                            vm.onCarbsChange(it)
                            nutritionCarbsError =
                                if (it.isBlank()) "Please enter the carbs" else null
                        },
                        label = { Text("Carbs") },
                        isError = nutritionCarbsError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    )
                    OutlinedTextField(
                        value = uiState!!.recipeModel.nutrition.fat.toString(),
                        onValueChange = {
                            vm.onFatChange(it)
                            nutritionFatsError =
                                if (it.isBlank()) "Please enter the fats" else null
                        },
                        label = { Text("Fats") },

                        isError = nutritionFatsError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    )
                }
                Spacer(Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ingredients")
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Add Icon",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = LocalIndication.current,
                                onClick = {
                                    vm.onIngredientAdd()
                                }
                            )
                    )
                }
                uiState!!.recipeModel.ingredients.forEachIndexed { index, ingredient ->
                    IngredientRow(
                        ingredientModel = ingredient,
                        onIngredientNameChange = { newIngredientName ->
                            vm.onIngredientItemChange(index, newIngredientName)

                        },
                        onIngredientAmountChange = { newIngredientAmount ->
                            vm.onIngredientAmountChange(index, newIngredientAmount)
                        },
                        onIngredientRemove = {
                            vm.onIngredientRemove(index)
                        })
                }
                Spacer(Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Instructions")
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Add Icon",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = LocalIndication.current,
                                onClick = {
                                    vm.onInstructionAdd()
                                }
                            )
                    )
                }
                uiState!!.recipeModel.instructions.forEachIndexed { index, instruction ->
                    InstructionRow(
                        instruction = instruction,
                        onInstructionChange = { newInstruction ->
                            vm.onInstructionChange(index, newInstruction)
                        },
                        onInstructionRemove = {
                            vm.onInstructionRemove(index)
                        })
                }

            }
        }
    }
}