package com.abid.recipeapp.ui.screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.data.model.IngredientModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.ui.components.create_recipe.IngredientRow
import com.abid.recipeapp.ui.components.create_recipe.InstructionRow
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.viewmodel.CreateRecipeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(vm: CreateRecipeViewModel = hiltViewModel(), onClickBackBtn: () -> Unit) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var selectedCategory by rememberSaveable { mutableStateOf<RecipeCategoriesDataModel?>(null) }
    var selectedCuisines by rememberSaveable { mutableStateOf<RecipeCuisinesDataModel?>(null) }
    var selectedDiffLvl by rememberSaveable { mutableStateOf<RecipeDiffLvlDataModel?>(null) }


    var recipeName by rememberSaveable { mutableStateOf("") }
    var recipeNameError by remember { mutableStateOf<String?>(null) }

    var desc by rememberSaveable { mutableStateOf("") }
    var descError by remember { mutableStateOf<String?>(null) }

    var prepTime by rememberSaveable { mutableStateOf("") }
    var prepTimeError by remember { mutableStateOf<String?>(null) }

    var cookTimeMin by rememberSaveable { mutableStateOf("") }
    var cookTimeMinError by remember { mutableStateOf<String?>(null) }

    var servings by rememberSaveable { mutableStateOf("") }
    var servingsError by remember { mutableStateOf<String?>(null) }

    var nutritionCalories by rememberSaveable { mutableStateOf("") }
    var nutritionCaloriesError by remember { mutableStateOf<String?>(null) }

    var nutritionProtein by rememberSaveable { mutableStateOf("") }
    var nutritionProteinError by remember { mutableStateOf<String?>(null) }

    var nutritionCarbs by rememberSaveable { mutableStateOf("") }
    var nutritionCarbsError by remember { mutableStateOf<String?>(null) }

    var nutritionFats by rememberSaveable { mutableStateOf("") }
    var nutritionFatsError by remember { mutableStateOf<String?>(null) }

    var ingredientRowList by remember {
        mutableStateOf(
            mutableListOf(
                IngredientModel("", "")
            )
        )
    }
    var instructionsRowList by remember {
        mutableStateOf(
            mutableListOf(
                ""
            )
        )
    }

    val uiState by vm.uiState.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        vm.loadRecipeCategories()
        vm.loadCuisines()
        vm.loadRecipeDifficulties()
    }
    LaunchedEffect(ingredientRowList.size + instructionsRowList.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TopBar(onClickBackBtn = onClickBackBtn, onClickSubmitBtn = {
                        vm.saveRecipe(
                            RecipeModel(
                                title = recipeName,
                                description = desc,
                                prepTime = prepTime.toInt(),
                                cookTime = cookTimeMin.toInt(),
                                servings = servings.toInt(),
                                category = selectedCategory?.label!!,
                                cuisine = selectedCuisines?.label!!,
                                difficulty = selectedDiffLvl?.label!!,
                                ingredients = ingredientRowList,
                                instructions = instructionsRowList
                            )
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
            Column(Modifier.verticalScroll(scrollState)) {
                OutlinedTextField(
                    value = recipeName,
                    onValueChange = {
                        recipeName = it
                        recipeNameError = if (it.isBlank()) "Recipe Title cannot be empty" else null
                    },
                    label = { Text("Recipe Title") },
                    isError = recipeNameError != null,
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
                        value = prepTime,
                        onValueChange = {
                            prepTime = it
                            prepTimeError =
                                if (it.isBlank()) "Description cannot be empty" else null
                        },
                        label = { Text("Prep Time(min)") },
                        isError = prepTimeError != null,
                        modifier = Modifier
                            .weight(1f),
                    )
                    Spacer(Modifier.width(5.dp))
                    OutlinedTextField(
                        value = cookTimeMin,
                        onValueChange = {
                            cookTimeMin = it
                            cookTimeMinError =
                                if (it.isBlank()) "Description cannot be empty" else null
                        },
                        label = { Text("Cook Time(min)") },
                        isError = cookTimeMinError != null,
                        modifier = Modifier
                            .weight(1f),
                    )
                }
                OutlinedTextField(
                    value = servings,
                    onValueChange = {
                        servings = it
                        servingsError = if (it.isBlank()) "Recipe Title cannot be empty" else null
                    },
                    label = { Text("Servings") },
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
                                containerColor = if (item == selectedCategory) {
                                    Orange40
                                } else {
                                    Color.Unspecified
                                }
                            ),
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    selectedCategory = item
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
                                containerColor = if (item == selectedCuisines) {
                                    Orange40
                                } else {
                                    Color.Unspecified
                                }
                            ),
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    selectedCuisines = item
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
                                containerColor = if (item == selectedDiffLvl) {
                                    Orange40
                                } else {
                                    Color.Unspecified
                                }
                            ),
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    selectedDiffLvl = item
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
                        value = nutritionCalories,
                        onValueChange = {
                            nutritionCalories = it
                            nutritionCaloriesError =
                                if (it.isBlank()) "Please enter the calories" else null
                        },
                        label = { Text("Calories") },
                        isError = nutritionCaloriesError != null,
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    )
                    OutlinedTextField(
                        value = nutritionProtein,
                        onValueChange = {
                            nutritionProtein = it
                            nutritionProteinError =
                                if (it.isBlank()) "Please enter the protein" else null
                        },
                        label = { Text("Calories") },
                        isError = nutritionProteinError != null,
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = nutritionCarbs,
                        onValueChange = {
                            nutritionCarbs = it
                            nutritionCarbsError =
                                if (it.isBlank()) "Please enter the carbs" else null
                        },
                        label = { Text("Carbs") },
                        isError = nutritionCarbsError != null,
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    )
                    OutlinedTextField(
                        value = nutritionFats,
                        onValueChange = {
                            nutritionFats = it
                            nutritionFatsError =
                                if (it.isBlank()) "Please enter the fats" else null
                        },
                        label = { Text("Fats") },
                        isError = nutritionFatsError != null,
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
                                    coroutineScope.launch {
                                        ingredientRowList =
                                            (ingredientRowList + IngredientModel(
                                                "",
                                                ""
                                            )).toMutableList()
                                    }
                                }
                            )
                    )
                }
                ingredientRowList.forEachIndexed { index, ingredient ->
                    IngredientRow(
                        ingredientModel = ingredient,
                        onIngredientNameChange = { newIngredientName ->
                            ingredientRowList = ingredientRowList.toMutableList().also {
                                it[index] = it[index].copy(item = newIngredientName)
                            }

                        },
                        onIngredientAmountChange = { newIngredientAmount ->
                            ingredientRowList = ingredientRowList.toMutableList().also {
                                it[index] = it[index].copy(amount = newIngredientAmount)
                            }
                        },
                        onIngredientRemove = {
                            ingredientRowList =
                                ingredientRowList.filterIndexed { i, _ -> i != index }
                                    .toMutableList()
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
                                    coroutineScope.launch {
                                        instructionsRowList =
                                            (instructionsRowList + "").toMutableList()
                                    }
                                }
                            )
                    )
                }
                instructionsRowList.forEachIndexed { index, instruction ->
                    InstructionRow(
                        instruction = instruction,
                        onInstructionChange = { newInstruction ->
                            instructionsRowList = instructionsRowList.toMutableList().also {
                                it[index] = newInstruction
                            }
                        },
                        onInstructionRemove = {
                            instructionsRowList =
                                instructionsRowList.filterIndexed { i, _ -> i != index }
                                    .toMutableList()
                        })
                }

            }
        }
    }
}


@Composable
private fun TopBar(onClickBackBtn: () -> Unit, onClickSubmitBtn: () -> Unit) {
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
                contentDescription = "Back button",
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current,
                    onClick = onClickBackBtn
                )
            )
            Text("Create Recipe")
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "Back button",
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current,
                    onClick = onClickSubmitBtn
                )
            )
        }
    }
}