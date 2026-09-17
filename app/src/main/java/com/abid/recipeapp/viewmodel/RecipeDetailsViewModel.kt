package com.abid.recipeapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.navigation.NavHelper
import com.abid.recipeapp.ui.state.RecipeDetailsUiState
import com.abid.recipeapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: String? = savedStateHandle[NavHelper.NAV_ARGS_RECIPE_ID_KEY]
    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        observeIngredientsTabs()
        observeFavStatus(recipeId)

        loadRecipeById(recipeId)
        refreshIngredientsTabs()
        fetchIsInFav(recipeId)
    }


    fun toggleFav(recipeId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isFavLoading = true, favErrorMsg = null) }
            try {
                repository.toggleFav(recipeId)
                _uiState.update { it.copy(isFavLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isFavLoading = false, favErrorMsg = e.message) }
            }
        }
    }

    private fun fetchIsInFav(recipeId: String?) {
        if (recipeId == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isFavLoading = true, favErrorMsg = null) }
            try {
                val isFav = repository.isFavLocally(recipeId)
                _uiState.update {
                    it.copy(
                        isFavLoading = false,
                        isFav = isFav.firstOrNull() ?: false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isFavLoading = false, favErrorMsg = e.message) }
            }
        }
    }

    private fun observeIngredientsTabs() {
        viewModelScope.launch {
            repository.getIngredientsTabsItems().collect { tabItems ->
                _uiState.update { it.copy(ingredientsTabList = tabItems) }
            }
        }
    }

    private fun observeFavStatus(recipeId: String?) {
        viewModelScope.launch {
            if (recipeId.isNullOrBlank()) {
                _uiState.update { it.copy(favErrorMsg = Constants.RECIPE_NOT_FOUND_ERROR_MSG) }
                return@launch
            }
            repository.isFavLocally(recipeId)
                .catch { e -> _uiState.update { it.copy(favErrorMsg = e.message) } }
                .collect { isFav ->
                    _uiState.update { it.copy(isFav = isFav) }
                }
        }
    }

    fun refreshIngredientsTabs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isIngredientsTabsLoading = true, ingredientsTabsErrorMsg = null)
            }
            try {
                repository.refreshIngredientsTabsItems()
                _uiState.update { it.copy(isIngredientsTabsLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isIngredientsTabsLoading = false, ingredientsTabsErrorMsg = e.message)
                }
            }
        }
    }

    fun loadRecipeById(recipeId: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isRecipeLoading = true, recipeErrorMsg = null) }
            try {
                val data = repository.getRecipeById(recipeId)
                if (data != null) {
                    _uiState.update { it.copy(isRecipeLoading = false, recipe = data) }
                } else {
                    _uiState.update {
                        it.copy(
                            isRecipeLoading = false,
                            recipeErrorMsg = "Recipe not found, going back..."
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRecipeLoading = false, recipeErrorMsg = e.message) }
            }
        }
    }
}