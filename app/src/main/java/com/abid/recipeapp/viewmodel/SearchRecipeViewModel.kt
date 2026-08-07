package com.abid.recipeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.remote.RecipeRepository
import com.abid.recipeapp.ui.state.SearchFilterOptionsUiState
import com.abid.recipeapp.ui.state.SearchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _searchResultState = MutableLiveData(SearchResultUiState())
    val searchResultState: LiveData<SearchResultUiState> = _searchResultState

    private val _searchFilterState = MutableLiveData(SearchFilterOptionsUiState())
    val searchFilterState: LiveData<SearchFilterOptionsUiState> = _searchFilterState

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
                _searchResultState.value = _searchResultState.value?.copy(isLoading = true)
                val data = repository.getAndSaveAllRecipes(
                    categoryName = selectedCategoryName,
                    cuisineName = selectedCuisinesName,
                    diffLvl = selectedDiffLvlName,
                    title = searchQueryRecipeTitle
                )
                _searchResultState.value =
                    _searchResultState.value?.copy(recipeList = data, isLoading = false)
            } catch (e: Exception) {
                _searchResultState.value =
                    _searchResultState.value?.copy(errorMsg = e.message, isLoading = false)
            }
        }
    }


    private fun loadRecipeCategories() {
        viewModelScope.launch {
            try {
                _searchFilterState.value =
                    _searchFilterState.value?.copy(isCategoriesLoading = true)
                val data = repository.getRecipeCategories()
                val finalData = listOf(RecipeCategoriesDataModel("-1", "All")) + data
                _searchFilterState.value =
                    _searchFilterState.value?.copy(
                        recipeCategories = finalData,
                        isCategoriesLoading = false
                    )
            } catch (e: Exception) {
                _searchFilterState.value =
                    _searchFilterState.value?.copy(
                        categoriesErrorMsg = e.message,
                        isCategoriesLoading = false
                    )
            }
        }
    }

    private fun loadRecipeCuisines() {
        viewModelScope.launch {
            try {
                _searchFilterState.value = _searchFilterState.value?.copy(isCuisinesLoading = true)
                val data = repository.getCuisines()
                _searchFilterState.value = _searchFilterState.value?.copy(
                    recipeCuisines = data,
                    isCuisinesLoading = false
                )
            } catch (e: Exception) {
                _searchFilterState.value =
                    _searchFilterState.value?.copy(
                        cuisinesErrorMsg = e.message,
                        isCuisinesLoading = false
                    )
            }
        }
    }

    private fun loadRecipeDifficultyLvl() {
        viewModelScope.launch {
            try {
                _searchFilterState.value = _searchFilterState.value?.copy(isDiffLvlLoading = true)
                val data = repository.getRecipeDifficulties()
                _searchFilterState.value =
                    _searchFilterState.value?.copy(
                        recipeDifficulties = data,
                        isDiffLvlLoading = false
                    )
            } catch (e: Exception) {
                _searchFilterState.value =
                    _searchFilterState.value?.copy(
                        diffLvlErrorMsg = e.message,
                        isDiffLvlLoading = false
                    )
            }
        }
    }
}