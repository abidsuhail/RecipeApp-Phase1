package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.remote.RecipeRepository
import com.abid.recipeapp.ui.state.RecipeDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(RecipeDetailsUiState(isLoading = true))
    val uiState: LiveData<RecipeDetailsUiState> = _uiState

    fun toggleFav(recipeId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                repository.toggleFav(recipeId)
                val isInFav = repository.isFav(recipeId)
                _uiState.value = _uiState.value?.copy(isLoading = false, isFav = isInFav)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(errorMsg = e.message, isLoading = false)
            }
        }
    }

    fun fetchIsInFav(recipeId: String?) {
        viewModelScope.launch {
            try {
                if (recipeId == null) {
                    return@launch
                }
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val isInFav = repository.isFav(recipeId)
                _uiState.value = _uiState.value?.copy(isFav = isInFav)
                _uiState.value = _uiState.value?.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(errorMsg = e.message, isLoading = false)
            }
        }
    }

    fun loadIngredientsTabs() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val data = repository.getIngredientsTabsItems()
                if (data != null) {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, ingredientsTabList = data)
                } else {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, errorMsg = "Ingredients not found")
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    fun loadRecipeById(recipeId: String?) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val data = repository.getRecipeById(recipeId)
                if (data != null) {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, recipe = data)
                } else {
                    _uiState.value =
                        _uiState.value?.copy(
                            isLoading = false,
                            errorMsg = "Recipe not found, going back..."
                        )

                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }
}