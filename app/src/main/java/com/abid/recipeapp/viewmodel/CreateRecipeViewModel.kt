package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.CreateRecipeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(CreateRecipeUiState(isLoading = true))
    val uiState: LiveData<CreateRecipeUiState> = _uiState


    fun loadRecipeCategories() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val data = repository.getRecipeCategories()
                if (data != null) {
                    _uiState.value = _uiState.value?.copy(isLoading = false, categoriesList = data)
                } else {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, errorMsg = "Categories not found")
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    fun loadCuisines() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val data = repository.getCuisines()
                if (data != null) {
                    _uiState.value = _uiState.value?.copy(isLoading = false, cuisinesList = data)
                } else {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, errorMsg = "Cuisines not found")
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    fun loadRecipeDifficulties() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val data = repository.getRecipeDifficulties()
                if (data != null) {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, difficultiesList = data)
                } else {
                    _uiState.value =
                        _uiState.value?.copy(
                            isLoading = false,
                            errorMsg = "Difficulty level not found"
                        )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }
}