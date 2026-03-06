package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.RecipeCategoriesUiState
import com.abid.recipeapp.ui.state.RecipeCuisinesUiState
import com.abid.recipeapp.ui.state.RecipeDifficultiesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _allCategories =
        MutableLiveData<RecipeCategoriesUiState>(RecipeCategoriesUiState.Loading)
    val allCategories: LiveData<RecipeCategoriesUiState> = _allCategories

    private val _allCuisines =
        MutableLiveData<RecipeCuisinesUiState>(RecipeCuisinesUiState.Loading)
    val allCuisines: LiveData<RecipeCuisinesUiState> = _allCuisines

    private val _allDifficulties =
        MutableLiveData<RecipeDifficultiesUiState>(RecipeDifficultiesUiState.Loading)
    val allDifficulties: LiveData<RecipeDifficultiesUiState> = _allDifficulties

    fun loadRecipeCategories() {
        viewModelScope.launch {
            try {
                _allCategories.value = RecipeCategoriesUiState.Loading
                val data = repository.getRecipeCategories()
                if (data != null) {
                    _allCategories.value = RecipeCategoriesUiState.Success(data)
                } else {
                    _allCategories.value = RecipeCategoriesUiState.NotFound("Categories not found!")
                }
            } catch (e: Exception) {
                _allCategories.value =
                    RecipeCategoriesUiState.Error("There are no recipes categories!")
            }
        }
    }

    fun loadCuisines() {
        viewModelScope.launch {
            try {
                _allCuisines.value = RecipeCuisinesUiState.Loading
                val data = repository.getCuisines()
                if (data != null) {
                    _allCuisines.value = RecipeCuisinesUiState.Success(data)
                } else {
                    _allCuisines.value =
                        RecipeCuisinesUiState.NotFound("Cuisines not found")
                }
            } catch (e: Exception) {
                _allCuisines.value = RecipeCuisinesUiState.Error("${e.message}, going back...")
            }
        }
    }

    fun loadRecipeDifficulties() {
        viewModelScope.launch {
            try {
                _allDifficulties.value = RecipeDifficultiesUiState.Loading
                val data = repository.getRecipeDifficulties()
                if (data != null) {
                    _allDifficulties.value = RecipeDifficultiesUiState.Success(data)
                } else {
                    _allDifficulties.value =
                        RecipeDifficultiesUiState.NotFound("Recipe Difficulties not found")
                }
            } catch (e: Exception) {
                _allDifficulties.value =
                    RecipeDifficultiesUiState.Error("${e.message}")
            }
        }
    }
}