package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.RecipeDetailUiState
import com.abid.recipeapp.ui.state.RecipeListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _allRecipes = MutableLiveData<RecipeListUiState>(RecipeListUiState.Loading)
    val allRecipes: LiveData<RecipeListUiState> = _allRecipes
    private val _foundRecipe = MutableLiveData<RecipeDetailUiState>(RecipeDetailUiState.Loading)
    val foundRecipe: LiveData<RecipeDetailUiState> = _foundRecipe

    fun loadAllRecipes() {
        viewModelScope.launch {
            try {
                _allRecipes.value = RecipeListUiState.Loading
                val data = repository.getAllRecipes()
                _allRecipes.value = RecipeListUiState.Success(data)
            } catch (e: Exception) {
                _allRecipes.value = RecipeListUiState.Error("There are no recipes available!")
            }
        }
    }

    fun loadRecipe(recipeId: String?) {
        viewModelScope.launch {
            try {
                _foundRecipe.value = RecipeDetailUiState.Loading
                val data = repository.getRecipeById(recipeId)
                if (data != null) {
                    _foundRecipe.value = RecipeDetailUiState.Success(data)
                } else {
                    _foundRecipe.value =
                        RecipeDetailUiState.NotFound("Recipe not found, going back...")
                }
            } catch (e: Exception) {
                _foundRecipe.value = RecipeDetailUiState.Error("${e.message}, going back...")
            }
        }
    }
}