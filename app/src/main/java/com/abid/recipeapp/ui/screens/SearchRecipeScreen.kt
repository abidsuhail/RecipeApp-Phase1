package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.ui.components.RecipeSearchBar
import com.abid.recipeapp.ui.state.SearchRecipeUiState
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Constants.CATEGORY
import com.abid.recipeapp.util.Constants.CUISINE
import com.abid.recipeapp.util.Constants.DIFFICULTY
import com.abid.recipeapp.util.Constants.SEARCH_RECIPE_SCREEN
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.viewmodel.SearchRecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRecipeScreen(vm: SearchRecipeViewModel = hiltViewModel()) {
    var showSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState()

    val state by vm.state.observeAsState(initial = SearchRecipeUiState())
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    var selectedCuisinesId by remember { mutableStateOf<String?>(null) }
    var selectedDiffLvlId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        vm.loadRecipeCategories()
        vm.loadRecipeCuisines()
        vm.loadRecipeDifficultyLvl()
    }

    Box(Modifier.padding(Dims.Dp10)) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(SEARCH_RECIPE_SCREEN, fontSize = Dims.Sp35)
            RecipeSearchBar()
        }
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier
                        .padding(Dims.Dp15)
                        .verticalScroll(rememberScrollState()),
                ) {
                    CategoryChipsGrid(
                        state.recipesCategories, selectedCategoryId
                    ) {
                        selectedCategoryId = it
                    }
                    CuisineChipsGrid(state.recipesCuisines, selectedCuisinesId) {
                        selectedCuisinesId = it
                    }
                    DifficultyChipsGrid(state.recipeDifficulties, selectedDiffLvlId) {
                        selectedDiffLvlId = it
                    }
                }
            }
        }
    }

}

@Composable
fun CategoryChipsGrid(
    recipeCategories: List<RecipeCategoriesDataModel>,
    selectedCategoryId: String?,
    onChipClicked: (String) -> Unit
) {
    Column {
        Text(CATEGORY, fontSize = Dims.Sp20, fontWeight = FontWeight.SemiBold)
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dims.Dp12),
            horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
            verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
        ) {
            recipeCategories.forEach { item ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (item.id == selectedCategoryId) {
                            Orange40
                        } else {
                            Color.Unspecified
                        }
                    ),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            onChipClicked(item.id)
                        }
                    )
                ) {
                    Text(item.label, modifier = Modifier.padding(Dims.Dp10))
                }
            }
        }
    }
}

@Composable
fun CuisineChipsGrid(
    recipeCuisines: List<RecipeCuisinesDataModel>,
    selectedCuisineId: String?,
    onChipClicked: (String) -> Unit
) {
    Column {
        Text(CUISINE, fontSize = Dims.Sp20, fontWeight = FontWeight.SemiBold)
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dims.Dp12),
            horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
            verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
        ) {
            recipeCuisines.forEach { item ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (item.id == selectedCuisineId) {
                            Orange40
                        } else {
                            Color.Unspecified
                        }
                    ),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            onChipClicked(item.id)
                        }
                    )
                ) {
                    Text(item.label, modifier = Modifier.padding(Dims.Dp10))
                }
            }
        }
    }
}

@Composable
fun DifficultyChipsGrid(
    recipeDifficulties: List<RecipeDiffLvlDataModel>,
    selectedDiffLvlId: String?,
    onChipClicked: (String) -> Unit
) {
    Column {
        Text(DIFFICULTY, fontSize = Dims.Sp20, fontWeight = FontWeight.SemiBold)
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dims.Dp12),

            horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
            verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
        ) {
            recipeDifficulties.forEach { item ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (item.id == selectedDiffLvlId) {
                            Orange40
                        } else {
                            Color.Unspecified
                        }
                    ),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            onChipClicked(item.id)
                        }
                    )
                ) {
                    Text(item.label.name, modifier = Modifier.padding(Dims.Dp10))
                }
            }
        }
    }
}