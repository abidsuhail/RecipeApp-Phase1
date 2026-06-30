package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.SearchRecipeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _state = MutableLiveData(SearchRecipeUiState(isLoading = true))
    val state: LiveData<SearchRecipeUiState> = _state

    fun loadSearchRecipe(
        categoryName: String? = null,
        cuisineName: String? = null,
        diffLvl: String? = null,
        title: String? = null
    ) {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isLoading = true)
                val data = repository.getAllRecipes(
                    categoryName = categoryName,
                    cuisineName = cuisineName,
                    diffLvl = diffLvl,
                    title = title
                )
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

    fun loadRecipeCuisines() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isLoading = true)
                val data = repository.getCuisines()
                _state.value = _state.value?.copy(recipesCuisines = data, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value?.copy(errorMsg = e.message)
            }
        }
    }

    fun loadRecipeDifficultyLvl() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isLoading = true)
                val data = repository.getRecipeDifficulties()
                _state.value = _state.value?.copy(recipeDifficulties = data, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value?.copy(errorMsg = e.message)
            }
        }
    }
}