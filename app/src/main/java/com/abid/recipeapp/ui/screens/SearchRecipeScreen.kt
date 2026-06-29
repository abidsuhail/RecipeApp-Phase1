package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.components.RecipeList
import com.abid.recipeapp.ui.components.RecipeSearchBar
import com.abid.recipeapp.ui.state.SearchRecipeUiState
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.ui.theme.SearchRecipeTheme
import com.abid.recipeapp.util.Constants.APPLY_FILTERS
import com.abid.recipeapp.util.Constants.CATEGORY
import com.abid.recipeapp.util.Constants.CUISINE
import com.abid.recipeapp.util.Constants.DIFFICULTY
import com.abid.recipeapp.util.Constants.RESET
import com.abid.recipeapp.util.Constants.SEARCH_FILTERS
import com.abid.recipeapp.util.Constants.SEARCH_RECIPE_SCREEN
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.viewmodel.SearchRecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRecipeScreen(
    vm: SearchRecipeViewModel = hiltViewModel(),
    onClickRecipeItem: (RecipeModel) -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val state by vm.state.observeAsState(initial = SearchRecipeUiState())
    var selectedCategoryName by remember { mutableStateOf<String?>(null) }
    var selectedCuisinesName by remember { mutableStateOf<String?>(null) }
    var selectedDiffLvlName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        vm.loadRecipeCategories()
        vm.loadRecipeCuisines()
        vm.loadRecipeDifficultyLvl()
    }

    Box(Modifier.padding(Dims.Dp10)) {
        Column {
            Text(
                SEARCH_RECIPE_SCREEN,
                fontSize = Dims.Sp35,
                modifier = Modifier.padding(bottom = Dims.Dp15)
            )
            RecipeSearchBar(onFilterClick = {
                showSheet = true
            })
            if (state.recipeList.isNotEmpty()) {
                RecipeList(recipeList = state.recipeList, onClickRecipeItem = onClickRecipeItem)
            }
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
                    Text(SEARCH_FILTERS, fontSize = Dims.Sp30)

                    SearchHorizontalDivider()

                    CategoryChipsGrid(
                        state.recipesCategories,
                        selectedCategoryName,
                        onChipClicked = {
                            selectedCategoryName = it
                        }
                    )
                    CuisineChipsGrid(
                        state.recipesCuisines,
                        selectedCuisinesName,
                        onChipClicked = {
                            selectedCuisinesName = it
                        })
                    DifficultyChipsGrid(
                        state.recipeDifficulties,
                        selectedDiffLvlName,
                        onChipClicked = {
                            selectedDiffLvlName = it
                        })
                    SearchHorizontalDivider()
                    ApplyFilterRow(
                        onClickReset = {
                            selectedCategoryName = null
                            selectedCuisinesName = null
                            selectedDiffLvlName = null
                        },
                        onClickApply = {
                            showSheet = false
                            vm.loadAllRecipes(
                                categoryName = selectedCategoryName,
                                cuisineName = selectedCuisinesName,
                                diffLvl = selectedDiffLvlName
                            )
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun SearchHorizontalDivider() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dims.Dp15),
        thickness = Dims.Dp1,
        color = Color.Gray
    )
}

@Composable
fun RowScope.ApplyFilterButton(btnTxt: String, isResetBtn: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = Dims.Dp8),
        colors = ButtonDefaults.buttonColors()
            .copy(containerColor = if (isResetBtn) SearchRecipeTheme.applyFilterResetBtnBg else SearchRecipeTheme.applyFilterApplyBtnBg)

    ) {
        Text(btnTxt, color = SearchRecipeTheme.resetFilterTxtColor)
    }
}


@Composable
fun ApplyFilterRow(onClickReset: () -> Unit, onClickApply: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        ApplyFilterButton(RESET, true, onClick = onClickReset)
        ApplyFilterButton(APPLY_FILTERS, false, onClick = onClickApply)
    }
}

@Composable
fun CategoryChipsGrid(
    recipeCategories: List<RecipeCategoriesDataModel>,
    selectedCategoryName: String?,
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
                        containerColor = if (item.label == selectedCategoryName) {
                            Orange40
                        } else {
                            Color.Unspecified
                        }
                    ),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            onChipClicked(item.label)
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
    selectedCuisineName: String?,
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
                        containerColor = if (item.label == selectedCuisineName) {
                            Orange40
                        } else {
                            Color.Unspecified
                        }
                    ),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            onChipClicked(item.label)
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
    selectedDiffLvlName: String?,
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
                        containerColor = if (item.label.name == selectedDiffLvlName) {
                            Orange40
                        } else {
                            Color.Unspecified
                        }
                    ),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            onChipClicked(item.label.name)
                        }
                    )
                ) {
                    Text(item.label.name, modifier = Modifier.padding(Dims.Dp10))
                }
            }
        }
    }
}