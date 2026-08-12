package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.navigation.NavHelper
import com.abid.recipeapp.ui.state.RecipeDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: String? = savedStateHandle[NavHelper.NAV_ARGS_RECIPE_ID_KEY]
    private val _uiState = MutableLiveData(RecipeDetailsUiState())
    val uiState: LiveData<RecipeDetailsUiState> = _uiState

    init {
        observeIngredientsTabs()

        loadRecipeById(recipeId)
        refreshIngredientsTabs()
        fetchIsInFav(recipeId)
    }

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

    private fun fetchIsInFav(recipeId: String?) {
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

    private fun observeIngredientsTabs() {
        viewModelScope.launch {
            repository.getIngredientsTabsItems().collect { tabItems ->
                _uiState.value =
                    _uiState.value?.copy(ingredientsTabList = tabItems)
            }
        }
    }

    fun refreshIngredientsTabs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(
                isIngredientsTabsLoading = true,
                ingredientsTabsErrorMsg = null
            )
            try {
                repository.refreshIngredientsTabsItems()
                _uiState.value = _uiState.value?.copy(isIngredientsTabsLoading = false)
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