package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.RecipeListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableLiveData(RecipeListUiState())
    val state: LiveData<RecipeListUiState> = _state

    init {
        observeRecipes()
        observeCategories()
        refreshAllRecipes()
        refreshCategories()
    }

    private fun observeRecipes() {
        viewModelScope.launch {
            repository.getAllRecipes().collect { recipes ->
                _state.value = _state.value?.copy(recipeList = recipes)
            }
        }
    }

    private fun observeCategories() {
        viewModelScope.launch {
            repository.getRecipeCategories().collect { categories ->
                _state.value = _state.value?.copy(recipesCategories = categories)
            }
        }
    }

    private fun refreshAllRecipes() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isRecipesLoading = true, recipesErrorMsg = null)
            try {
                repository.refreshAllRecipes()
                _state.value = _state.value?.copy(isRecipesLoading = false)
            } catch (e: Exception) {
                _state.value =
                    _state.value?.copy(isRecipesLoading = false, recipesErrorMsg = e.message)
            }
        }
    }


    private fun refreshCategories() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isCategoriesLoading = true, categoriesErrorMsg = null)
            try {
                repository.refreshCategories()
                _state.value = _state.value?.copy(isCategoriesLoading = false)
            } catch (e: Exception) {
                _state.value =
                    _state.value?.copy(isCategoriesLoading = false, categoriesErrorMsg = e.message)
            }
        }
    }


    fun loadRecipesByFilter(categoryName: String? = null) {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isRecipesLoading = true)
                val data = repository.getRecipesByFilters(categoryName)
                _state.value =
                    _state.value?.copy(recipeList = data.first(), isRecipesLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value?.copy(recipesErrorMsg = e.message)
            }
        }
    }


}