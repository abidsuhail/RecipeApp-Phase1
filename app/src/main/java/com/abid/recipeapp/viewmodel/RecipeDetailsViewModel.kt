package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.RecipeDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(RecipeDetailsUiState())
    val uiState: LiveData<RecipeDetailsUiState> = _uiState

    fun toggleFav(recipeId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isFavLoading = true, favErrorMsg = null)
            try {
                repository.toggleFav(recipeId)
                val isInFav = repository.isFav(recipeId)
                _uiState.value = _uiState.value?.copy(isFavLoading = false, isFav = isInFav)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(isFavLoading = false, favErrorMsg = e.message)
            }
        }
    }

    fun fetchIsInFav(recipeId: String?) {
        if (recipeId == null) return

        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isFavLoading = true, favErrorMsg = null)
            try {
                val isInFav = repository.isFav(recipeId)
                _uiState.value = _uiState.value?.copy(isFavLoading = false, isFav = isInFav)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(isFavLoading = false, favErrorMsg = e.message)
            }
        }
    }

    fun loadIngredientsTabs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(
                isIngredientsTabsLoading = true,
                ingredientsTabsErrorMsg = null
            )
            try {
                val data = repository.getAndSaveIngredientsTabsItems()
                _uiState.value = _uiState.value?.copy(
                    isIngredientsTabsLoading = false,
                    ingredientsTabList = data
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(
                    isIngredientsTabsLoading = false,
                    ingredientsTabsErrorMsg = e.message
                )
            }
        }
    }

    fun loadRecipeById(recipeId: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isRecipeLoading = true, recipeErrorMsg = null)
            try {
                val data = repository.getRecipeById(recipeId)
                if (data != null) {
                    _uiState.value = _uiState.value?.copy(isRecipeLoading = false, recipe = data)
                } else {
                    _uiState.value = _uiState.value?.copy(
                        isRecipeLoading = false,
                        recipeErrorMsg = "Recipe not found, going back..."
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isRecipeLoading = false, recipeErrorMsg = e.message)
            }
        }
    }
}