package com.abid.recipeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.SearchRecipeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _state = MutableLiveData(SearchRecipeUiState())
    val state: LiveData<SearchRecipeUiState> = _state

    private var searchJob: Job? = null

    var searchQueryRecipeTitle by mutableStateOf("")
        private set

    var selectedCategoryName by mutableStateOf<String?>(null)
        private set

    var selectedCuisinesName by mutableStateOf<String?>(null)
        private set

    var selectedDiffLvlName by mutableStateOf<String?>(null)
        private set

    fun onSearchQueryChanged(query: String) {
        searchQueryRecipeTitle = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            loadSearchRecipe()
        }
    }

    fun onCategorySelected(name: String?) {
        selectedCategoryName = name
    }

    fun onCuisineSelected(name: String?) {
        selectedCuisinesName = name
    }

    fun onDiffLvlSelected(name: String?) {
        selectedDiffLvlName = name
    }

    fun onResetFilters() {
        selectedCategoryName = null
        selectedCuisinesName = null
        selectedDiffLvlName = null
    }

    fun loadInitialRecipes() {
        loadSearchRecipe()
    }

    fun onApplyFilters() {
        loadSearchRecipe()
    }

    fun loadBottomSheetChipsData() {
        loadRecipeCategories()
        loadRecipeCuisines()
        loadRecipeDifficultyLvl()
    }

    private fun loadSearchRecipe() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isSearchLoading = true)
                val data = repository.getAllRecipes(
                    categoryName = selectedCategoryName,
                    cuisineName = selectedCuisinesName,
                    diffLvl = selectedDiffLvlName,
                    title = searchQueryRecipeTitle
                )
                _state.value = _state.value?.copy(recipeList = data, isSearchLoading = false)
            } catch (e: Exception) {
                _state.value =
                    _state.value?.copy(searchErrorMsg = e.message, isSearchLoading = false)
            }
        }
    }


    private fun loadRecipeCategories() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isCategoriesLoading = true)
                val data = repository.getRecipeCategories()
                val finalData = listOf(RecipeCategoriesDataModel("-1", "All")) + data
                _state.value =
                    _state.value?.copy(recipesCategories = finalData, isCategoriesLoading = false)
            } catch (e: Exception) {
                _state.value =
                    _state.value?.copy(categoriesErrorMsg = e.message, isCategoriesLoading = false)
            }
        }
    }

    private fun loadRecipeCuisines() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isCuisinesLoading = true)
                val data = repository.getCuisines()
                _state.value = _state.value?.copy(recipesCuisines = data, isCuisinesLoading = false)
            } catch (e: Exception) {
                _state.value =
                    _state.value?.copy(cuisinesErrorMsg = e.message, isCuisinesLoading = false)
            }
        }
    }

    private fun loadRecipeDifficultyLvl() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isDiffLvlLoading = true)
                val data = repository.getRecipeDifficulties()
                _state.value =
                    _state.value?.copy(recipeDifficulties = data, isDiffLvlLoading = false)
            } catch (e: Exception) {
                _state.value =
                    _state.value?.copy(diffLvlErrorMsg = e.message, isDiffLvlLoading = false)
            }
        }
    }
}