package com.abid.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.RecipeListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeListUiState())
    val state: StateFlow<RecipeListUiState> = _state.asStateFlow()

    init {
        observeRecipes()
        observeCategories()
        refreshAllRecipes()
        refreshCategories()
    }

    private fun observeRecipes() {
        viewModelScope.launch {
            repository.getAllRecipes().collect { recipes ->
                _state.update { it.copy(recipeList = recipes) }
            }
        }
    }

    private fun observeCategories() {
        viewModelScope.launch {
            repository.getRecipeCategories().collect { categories ->
                _state.update { it.copy(recipesCategories = categories) }
            }
        }
    }

    private fun refreshAllRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isRecipesLoading = true, recipesErrorMsg = null) }
            try {
                repository.refreshAllRecipes()
                _state.update { it.copy(isRecipesLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isRecipesLoading = false, recipesErrorMsg = e.message) }
            }
        }
    }

    private fun refreshCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isCategoriesLoading = true, categoriesErrorMsg = null) }
            try {
                repository.refreshCategories()
                _state.update { it.copy(isCategoriesLoading = false) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isCategoriesLoading = false,
                        categoriesErrorMsg = e.message
                    )
                }
            }
        }
    }

    fun loadRecipesByFilter(categoryName: String? = null) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isRecipesLoading = true) }
                val data = repository.getRecipesByFilters(categoryName)
                _state.update { it.copy(recipeList = data.first(), isRecipesLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(recipesErrorMsg = e.message) }
            }
        }
    }


}