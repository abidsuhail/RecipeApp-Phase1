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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.viewmodel.CreateRecipeViewModel
import kotlinx.coroutines.launch

data class IngredientRowDataModel(
    var ingredientName: String,
    var ingredientAmount: String
)

data class InstructionRowDataModel(
    var instruction: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(vm: CreateRecipeViewModel = hiltViewModel(), onClickBackBtn: () -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    var desc by rememberSaveable { mutableStateOf("") }
    var descError by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var ingredientRowList by remember {
        mutableStateOf(
            mutableListOf(
                IngredientRowDataModel("", "")
            )
        )
    }
    var instructionsRowList by remember {
        mutableStateOf(
            mutableListOf(
                InstructionRowDataModel("")
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
                    TopBar(onClickBackBtn)
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
                            descError =
                                if (it.isBlank()) "Description cannot be empty" else null
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
                            descError =
                                if (it.isBlank()) "Description cannot be empty" else null
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
                    uiState?.categoriesList?.forEach { item ->
                        Card() {
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
                        Card() {
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
                        Card() {
                            Text(item.label.name, modifier = Modifier.padding(10.dp))
                        }
                    }
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
                                            (ingredientRowList + IngredientRowDataModel(
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
                        ingredientRowDataModel = ingredient,
                        onIngredientNameChange = { newIngredientName ->
                            ingredientRowList = ingredientRowList.toMutableList().also {
                                it[index] = it[index].copy(ingredientName = newIngredientName)
                            }

                        },
                        onIngredientAmountChange = { newIngredientAmount ->
                            ingredientRowList = ingredientRowList.toMutableList().also {
                                it[index] = it[index].copy(ingredientAmount = newIngredientAmount)
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
                                            (instructionsRowList + InstructionRowDataModel(
                                                "",
                                            )).toMutableList()
                                    }
                                }
                            )
                    )
                }
                instructionsRowList.forEachIndexed { index, instruction ->
                    InstructionRow(
                        instructionRowDataModel = instruction,
                        onInstructionChange = { newInstruction ->
                            instructionsRowList = instructionsRowList.toMutableList().also {
                                it[index] = it[index].copy(instruction = newInstruction)
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
private fun InstructionRow(
    instructionRowDataModel: InstructionRowDataModel,
    onInstructionChange: (String) -> Unit,
    onInstructionRemove: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = instructionRowDataModel.instruction,
            onValueChange = onInstructionChange,
            label = { Text("Ingredient name") },
            isError = instructionRowDataModel.instruction.isBlank(),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )

        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close icon",
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current,
                    onClick = {
                        onInstructionRemove()
                    }
                )
        )
    }
}

@Composable
private fun IngredientRow(
    ingredientRowDataModel: IngredientRowDataModel,
    onIngredientNameChange: (String) -> Unit,
    onIngredientAmountChange: (String) -> Unit,
    onIngredientRemove: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = ingredientRowDataModel.ingredientName,
            onValueChange = onIngredientNameChange,
            label = { Text("Ingredient name") },
            isError = ingredientRowDataModel.ingredientName.isBlank(),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        )
        OutlinedTextField(
            value = ingredientRowDataModel.ingredientAmount,
            onValueChange = onIngredientAmountChange,
            label = { Text("Amount") },
            isError = ingredientRowDataModel.ingredientAmount.isBlank(),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)

        )
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close icon",
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current,
                    onClick = {
                        onIngredientRemove()
                    }
                )
        )
    }
}

@Composable
private fun TopBar(onClickBackBtn: () -> Unit) {
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
                contentDescription = "Back button"
            )
        }
    }
}