package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.CreateRecipeUiState
import com.abid.recipeapp.ui.state.RecipeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _allCategories =
        MutableLiveData<CreateRecipeUiState>(CreateRecipeUiState.Loading)
    val allCategories: LiveData<CreateRecipeUiState> = _allCategories


    fun getRecipeCategories() {
        viewModelScope.launch {
            try {
                _allCategories.value = CreateRecipeUiState.Loading
                val data = repository.getRecipeCategories()
                _allCategories.value = CreateRecipeUiState.Success(data)
            } catch (e: Exception) {
                _allCategories.value =
                    CreateRecipeUiState.Error("There are no recipes categories!")
            }
        }
    }

    fun getCuisines() {
        viewModelScope.launch {
            try {
                _foundRecipe.value = RecipeDetailUiState.Loading
                val data = repository.getCuisines()
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

    fun getRecipeDifficulties() {
        viewModelScope.launch {
            try {
                _foundRecipe.value = RecipeDetailUiState.Loading
                val data = repository.getRecipeDifficulties()
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