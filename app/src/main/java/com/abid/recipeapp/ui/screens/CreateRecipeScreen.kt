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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.ui.components.create_recipe.CreateRecipeTopBar
import com.abid.recipeapp.ui.components.create_recipe.ImagePicker
import com.abid.recipeapp.ui.components.create_recipe.IngredientRow
import com.abid.recipeapp.ui.components.create_recipe.InstructionRow
import com.abid.recipeapp.ui.state.RecipeSaveState
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Constants.ADD_ICON
import com.abid.recipeapp.util.Constants.CALORIES
import com.abid.recipeapp.util.Constants.CARBS
import com.abid.recipeapp.util.Constants.CATEGORY
import com.abid.recipeapp.util.Constants.COOK_TIME_MIN
import com.abid.recipeapp.util.Constants.CUISINE
import com.abid.recipeapp.util.Constants.DESCRIPTION
import com.abid.recipeapp.util.Constants.DESCRIPTION_CANNOT_BE_EMPTY
import com.abid.recipeapp.util.Constants.DIFFICULTY
import com.abid.recipeapp.util.Constants.FATS
import com.abid.recipeapp.util.Constants.INGREDIENTS
import com.abid.recipeapp.util.Constants.INSTRUCTIONS
import com.abid.recipeapp.util.Constants.NUTRITION
import com.abid.recipeapp.util.Constants.PLEASE_ENTER_CALORIES
import com.abid.recipeapp.util.Constants.PLEASE_ENTER_CARBS
import com.abid.recipeapp.util.Constants.PLEASE_ENTER_FATS
import com.abid.recipeapp.util.Constants.PLEASE_ENTER_PROTEIN
import com.abid.recipeapp.util.Constants.PREP_TIME_MIN
import com.abid.recipeapp.util.Constants.PROTEIN
import com.abid.recipeapp.util.Constants.RECIPE_TITLE
import com.abid.recipeapp.util.Constants.RECIPE_TITLE_CANNOT_BE_EMPTY
import com.abid.recipeapp.util.Constants.SERVINGS
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.viewmodel.CreateRecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(
    vm: CreateRecipeViewModel = hiltViewModel(),
    onClickBackBtn: () -> Unit,
    handleGotoHomeScreen: () -> Unit
) {

    val scrollState = rememberScrollState()
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
                .padding(Dims.Dp12)
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
                        recipeNameError = if (it.isBlank()) RECIPE_TITLE_CANNOT_BE_EMPTY else null
                    },
                    label = { Text(RECIPE_TITLE) },
                    isError = recipeNameError != null,
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = uiState!!.recipeModel.description,
                    onValueChange = {
                        vm.onDescriptionChange(it)
                        descError = if (it.isBlank()) DESCRIPTION_CANNOT_BE_EMPTY else null
                    },
                    label = { Text(DESCRIPTION) },
                    isError = descError != null,
                    modifier = Modifier
                        .height(Dims.Dp140)
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
                                if (it.isBlank()) DESCRIPTION_CANNOT_BE_EMPTY else null
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(PREP_TIME_MIN) },
                        isError = prepTimeError != null,
                        modifier = Modifier
                            .weight(1f),
                    )
                    Spacer(Modifier.width(Dims.Dp5))
                    OutlinedTextField(
                        value = uiState!!.recipeModel.cookTime.toString(),
                        onValueChange = {
                            vm.onCookTimeChange(it)
                            cookTimeMinError =
                                if (it.isBlank()) DESCRIPTION_CANNOT_BE_EMPTY else null
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(COOK_TIME_MIN) },
                        isError = cookTimeMinError != null,
                        modifier = Modifier
                            .weight(1f),
                    )
                }
                OutlinedTextField(
                    value = uiState!!.recipeModel.servings.toString(),
                    onValueChange = {
                        vm.onServingsChange(it)
                        servingsError = if (it.isBlank()) RECIPE_TITLE_CANNOT_BE_EMPTY else null
                    },
                    label = { Text(SERVINGS) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = servingsError != null,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(Dims.Dp15))
                Text(CATEGORY)
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dims.Dp12),
                    horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
                    verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
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
                            Text(item.label, modifier = Modifier.padding(Dims.Dp10))
                        }
                    }

                }
                Spacer(Modifier.height(Dims.Dp15))
                Text(CUISINE)
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dims.Dp12),
                    horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
                    verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
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
                            Text(item.label, modifier = Modifier.padding(Dims.Dp10))
                        }
                    }
                }
                Spacer(Modifier.height(Dims.Dp15))
                Text(DIFFICULTY)
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dims.Dp12),
                    horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
                    verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
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
                            Text(item.label.name, modifier = Modifier.padding(Dims.Dp10))
                        }
                    }
                }
                Spacer(Modifier.height(Dims.Dp15))
                Text(NUTRITION)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = uiState!!.recipeModel.nutrition.calories.toString(),
                        onValueChange = {
                            vm.onCaloriesChange(it)
                            nutritionCaloriesError =
                                if (it.isBlank()) PLEASE_ENTER_CALORIES else null
                        },
                        label = { Text(CALORIES) },
                        isError = nutritionCaloriesError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(Dims.Dp5)
                    )
                    OutlinedTextField(
                        value = uiState!!.recipeModel.nutrition.protein.toString(),
                        onValueChange = {
                            vm.onProteinChange(it)
                            nutritionProteinError =
                                if (it.isBlank()) PLEASE_ENTER_PROTEIN else null
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(PROTEIN) },
                        isError = nutritionProteinError != null,
                        modifier = Modifier
                            .weight(1f)
                            .padding(Dims.Dp5)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = uiState!!.recipeModel.nutrition.carbs.toString(),
                        onValueChange = {
                            vm.onCarbsChange(it)
                            nutritionCarbsError =
                                if (it.isBlank()) PLEASE_ENTER_CARBS else null
                        },
                        label = { Text(CARBS) },
                        isError = nutritionCarbsError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(Dims.Dp5)
                    )
                    OutlinedTextField(
                        value = uiState!!.recipeModel.nutrition.fat.toString(),
                        onValueChange = {
                            vm.onFatChange(it)
                            nutritionFatsError =
                                if (it.isBlank()) PLEASE_ENTER_FATS else null
                        },
                        label = { Text(FATS) },

                        isError = nutritionFatsError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(Dims.Dp5)
                    )
                }
                Spacer(Modifier.height(Dims.Dp15))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(INGREDIENTS)
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = ADD_ICON,
                        modifier = Modifier
                            .size(Dims.Dp32)
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
                Spacer(Modifier.height(Dims.Dp15))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(INSTRUCTIONS)
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = ADD_ICON,
                        modifier = Modifier
                            .size(Dims.Dp32)
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