package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.RecipeListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableLiveData(RecipeListUiState(isLoading = true))
    val state: LiveData<RecipeListUiState> = _state

    fun loadAllRecipes() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isLoading = true)
                val data = repository.getAllRecipes()
                _state.value = _state.value?.copy(recipeList = data, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value?.copy(errorMsg = e.message)
            }
        }
    }

    fun loadRecipeCategories() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isLoading = true)
                val data = repository.getRecipeCategories()
                _state.value = _state.value?.copy(recipesCategories = data, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value?.copy(errorMsg = e.message)
            }
        }
    }


}