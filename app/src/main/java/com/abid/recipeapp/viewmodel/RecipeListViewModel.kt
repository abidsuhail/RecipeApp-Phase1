package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.remote.RecipeRemoteDataSource
import com.abid.recipeapp.ui.state.RecipeListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRemoteDataSource
) : ViewModel() {

    private val _state = MutableLiveData(RecipeListUiState(isLoading = true))
    val state: LiveData<RecipeListUiState> = _state


    fun loadAllRecipes(categoryName: String? = null) {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isLoading = true)
                val data = repository.getRecipesByFilter(categoryName)
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
                val finalData = listOf(RecipeCategoriesDataModel("-1", "All")) + data
                _state.value = _state.value?.copy(recipesCategories = finalData, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value?.copy(errorMsg = e.message)
            }
        }
    }


}