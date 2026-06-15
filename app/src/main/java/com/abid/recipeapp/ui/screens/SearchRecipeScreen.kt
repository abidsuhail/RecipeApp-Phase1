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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.ui.theme.Orange40
import com.abid.recipeapp.util.Constants.SEARCH_RECIPE_SCREEN
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.viewmodel.SearchRecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRecipeScreen(vm: SearchRecipeViewModel = hiltViewModel()) {
    var showSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState()

    val state by vm.state.observeAsState()

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
        }
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
            ) {
                // Sheet content
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dims.Dp12),
                        horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
                        verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
                    ) {
                        state?.recipesCategories?.forEach { item ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (false) {
                                        Orange40
                                    } else {
                                        Color.Unspecified
                                    }
                                ),
                                modifier = Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        // vm.onCategoryChange(item.label)
                                    }
                                )
                            ) {
                                Text(item.label, modifier = Modifier.padding(Dims.Dp10))
                            }
                        }
                    }
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dims.Dp12),
                        horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
                        verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
                    ) {
                        state?.recipesCuisines?.forEach { item ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (false) {
                                        Orange40
                                    } else {
                                        Color.Unspecified
                                    }
                                ),
                                modifier = Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        // vm.onCategoryChange(item.label)
                                    }
                                )
                            ) {
                                Text(item.label, modifier = Modifier.padding(Dims.Dp10))
                            }
                        }
                    }
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dims.Dp12),
                        horizontalArrangement = Arrangement.spacedBy(Dims.Dp8),
                        verticalArrangement = Arrangement.spacedBy(Dims.Dp8)
                    ) {
                        state?.recipeDifficulties?.forEach { item ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (false) {
                                        Orange40
                                    } else {
                                        Color.Unspecified
                                    }
                                ),
                                modifier = Modifier.clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        // vm.onCategoryChange(item.label)
                                    }
                                )
                            ) {
                                Text(item.label.name, modifier = Modifier.padding(Dims.Dp10))
                            }
                        }
                    }
                }
            }
        }
    }

}