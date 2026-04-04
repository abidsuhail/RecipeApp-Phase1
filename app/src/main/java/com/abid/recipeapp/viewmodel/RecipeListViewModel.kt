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

    private val _recipeListState = MutableLiveData<RecipeListUiState>(RecipeListUiState.Loading)
    val recipeListState: LiveData<RecipeListUiState> = _recipeListState

    private val _categoriesState = MutableLiveData<RecipeListUiState>(RecipeListUiState.Loading)
    val categoriesState: LiveData<RecipeListUiState> = _categoriesState

    fun loadAllRecipes() {
        viewModelScope.launch {
            try {
                _recipeListState.value = RecipeListUiState.Loading
                val data = repository.getAllRecipes()
                _recipeListState.value = RecipeListUiState.RecipeListSuccess(data)
            } catch (e: Exception) {
                _recipeListState.value =
                    RecipeListUiState.Error(e.message ?: "There are no recipes available!")
            }
        }
    }

    fun loadRecipeCategories() {
        viewModelScope.launch {
            try {
                _categoriesState.value = RecipeListUiState.Loading
                val data = repository.getRecipeCategories()
                _categoriesState.value = RecipeListUiState.RecipeCategoriesSuccess(data)
            } catch (e: Exception) {
                _categoriesState.value =
                    RecipeListUiState.Error(e.message ?: "There are no recipes available!")
            }
        }
    }


}