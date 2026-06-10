package com.abid.recipeapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.components.ErrorStateBox
import com.abid.recipeapp.ui.components.LoadingStateBox
import com.abid.recipeapp.ui.components.RecipeList
import com.abid.recipeapp.util.Constants
import com.abid.recipeapp.util.Constants.FAVOURITES_SCREEN_HEADING
import com.abid.recipeapp.util.Dims
import com.abid.recipeapp.viewmodel.FavScreenViewModel


@Composable
fun FavRecipeScreen(
    vm: FavScreenViewModel = hiltViewModel(),
    onClickRecipeItem: (RecipeModel) -> Unit
) {
    val state by vm.state.observeAsState()

    LaunchedEffect(Unit) {
        vm.loadAllFavList()
    }

    Box(modifier = Modifier.padding(Dims.Dp10)) {
        Column {
            Text(FAVOURITES_SCREEN_HEADING, fontSize = Dims.Sp35)
            if (state?.isLoading == true) {
                LoadingStateBox()
            } else if (!state?.errorMsg.isNullOrBlank()) {
                ErrorStateBox(state!!.errorMsg!!)
            } else if (state?.favRecipeList?.isNotEmpty() == true) {
                RecipeList(
                    recipeList = state?.favRecipeList,
                    onClickRecipeItem = onClickRecipeItem
                )
            } else if (state?.favRecipeList?.isEmpty() == true) {
                ErrorStateBox(Constants.FAVOURITE_NOT_FOUND_ERROR_MSG)
            }

        }
    }
}