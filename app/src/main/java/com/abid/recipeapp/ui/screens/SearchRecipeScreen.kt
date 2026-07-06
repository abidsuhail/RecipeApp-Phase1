package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.components.BottomSheetChipGridItem
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.ui.components.RecipeList
import com.abid.recipeapp.ui.components.RecipeSearchBar
import com.abid.recipeapp.ui.state.SearchRecipeUiState
import com.abid.recipeapp.ui.theme.LocalAppCustomColors
import com.abid.recipeapp.util.Constants.APPLY_FILTERS
import com.abid.recipeapp.util.Constants.CATEGORY
import com.abid.recipeapp.util.Constants.CUISINE
import com.abid.recipeapp.util.Constants.DIFFICULTY
import com.abid.recipeapp.util.Constants.RESET
import com.abid.recipeapp.util.Constants.SEARCH_FILTERS
import com.abid.recipeapp.util.Constants.SEARCH_RECIPE_SCREEN
import com.abid.recipeapp.util.Constants.UNABLE_TO_LOAD_CATEGORIES
import com.abid.recipeapp.util.Constants.UNABLE_TO_LOAD_CUISINES
import com.abid.recipeapp.util.Constants.UNABLE_TO_LOAD_DIFFICULTY_LEVELS
import com.abid.recipeapp.util.Constants.searchedRecipeNotFoundMsg
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.viewmodel.SearchRecipeViewModel
import kotlinx.coroutines.delay


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
    var enteredSearchTitle by remember { mutableStateOf<String?>(null) }

    fun fetchSearchRecipes() {
        vm.loadSearchRecipe(
            categoryName = selectedCategoryName,
            cuisineName = selectedCuisinesName,
            diffLvl = selectedDiffLvlName,
            title = enteredSearchTitle,
        )
    }

    LaunchedEffect(Unit) {
        vm.loadRecipeCategories()
        vm.loadRecipeCuisines()
        vm.loadRecipeDifficultyLvl()
    }
    LaunchedEffect(enteredSearchTitle) {
        if (enteredSearchTitle.isNullOrBlank()) {
            fetchSearchRecipes()
        } else {
            delay(500)
            fetchSearchRecipes()
        }
    }


    Box(Modifier.padding(Dims.Dp10)) {
        Column {
            Text(
                SEARCH_RECIPE_SCREEN,
                fontSize = Dims.Sp35,
                modifier = Modifier.padding(bottom = Dims.Dp15)
            )
            RecipeSearchBar(
                onFilterClick = {
                    showSheet = true
                },
                onTxtEntered = {
                    enteredSearchTitle = it
                })

            if (state.isSearchLoading) {
                LoadingStateBox()
            } else if (state.recipeList.isNotEmpty()) {
                RecipeList(recipeList = state.recipeList, onClickRecipeItem = onClickRecipeItem)
            } else {
                ErrorStateBox(searchedRecipeNotFoundMsg(enteredSearchTitle))
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
                    BottomSheetHeader(state.isCategoriesLoading || state.isCuisinesLoading || state.isDiffLvlLoading)

                    SearchHorizontalDivider()

                    if (state.categoriesErrorMsg.isNullOrBlank()) {
                        BottomSheetChipGridItem(
                            title = CATEGORY,
                            labelOf = { it.label },
                            items = state.recipesCategories,
                            selectedLabel = selectedCategoryName,
                            onChipClicked = {
                                selectedCategoryName = it
                            }
                        )
                    } else {
                        ErrorStateBox(UNABLE_TO_LOAD_CATEGORIES)
                    }
                    if (state.cuisinesErrorMsg.isNullOrBlank()) {
                        BottomSheetChipGridItem(
                            title = CUISINE,
                            labelOf = { it.label },
                            items = state.recipesCuisines,
                            selectedLabel = selectedCuisinesName,
                            onChipClicked = {
                                selectedCuisinesName = it
                            }
                        )
                    } else {
                        ErrorStateBox(UNABLE_TO_LOAD_CUISINES)
                    }
                    if (state.diffLvlErrorMsg.isNullOrBlank()) {
                        BottomSheetChipGridItem(
                            title = DIFFICULTY,
                            labelOf = { it.label.name },
                            items = state.recipeDifficulties,
                            selectedLabel = selectedDiffLvlName,
                            onChipClicked = {
                                selectedDiffLvlName = it
                            }
                        )
                    } else {
                        ErrorStateBox(UNABLE_TO_LOAD_DIFFICULTY_LEVELS)
                    }
                    SearchHorizontalDivider()
                    ApplyFilterRow(
                        onClickReset = {
                            selectedCategoryName = null
                            selectedCuisinesName = null
                            selectedDiffLvlName = null
                        },
                        onClickApply = {
                            showSheet = false
                            fetchSearchRecipes()
                        }
                    )
                }
            }
        }
    }

}

@Composable
private fun BottomSheetHeader(isBottomSheetItemsLoading: Boolean) {
    Row {
        Text(
            SEARCH_FILTERS,
            fontSize = Dims.Sp30,
            modifier = Modifier.weight(Dims.W1)
        )
        if (isBottomSheetItemsLoading) {
            LoadingStateBox(modifier = Modifier.wrapContentSize())
        }
    }
}

@Composable
private fun SearchHorizontalDivider() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dims.Dp15),
        thickness = Dims.Dp1,
        color = Color.Gray
    )
}

@Composable
private fun RowScope.ApplyFilterButton(btnTxt: String, isResetBtn: Boolean, onClick: () -> Unit) {
    val customColors = LocalAppCustomColors.current
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = Dims.Dp8),
        colors = ButtonDefaults.buttonColors()
            .copy(containerColor = if (isResetBtn) customColors.searchApplyFilterResetBtnBg else customColors.searchApplyFilterApplyBtnBg)

    ) {
        Text(btnTxt, color = customColors.searchResetFilterTxtColor)
    }
}


@Composable
private fun ApplyFilterRow(onClickReset: () -> Unit, onClickApply: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        ApplyFilterButton(RESET, true, onClick = onClickReset)
        ApplyFilterButton(APPLY_FILTERS, false, onClick = onClickApply)
    }
}