package com.abid.recipeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.SearchFilterOptionsUiState
import com.abid.recipeapp.ui.state.SearchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {


    private val _searchResultState = MutableStateFlow(SearchResultUiState())
    val searchResultState: StateFlow<SearchResultUiState> = _searchResultState.asStateFlow()

    private val _searchFilterState = MutableStateFlow(SearchFilterOptionsUiState())
    val searchFilterState: StateFlow<SearchFilterOptionsUiState> = _searchFilterState.asStateFlow()

    private var searchJob: Job? = null

    var searchQueryRecipeTitle by mutableStateOf("")
        private set

    var selectedCategoryName by mutableStateOf<String?>(null)
        private set

    var selectedCuisinesName by mutableStateOf<String?>(null)
        private set

    var selectedDiffLvlName by mutableStateOf<String?>(null)
        private set

    init {
        observeCategories()
        observeDiffLvls()
        observeCuisines()
        loadBottomSheetChipsData()
        loadInitialRecipes()
    }

    fun onSearchQueryChanged(query: String) {
        searchQueryRecipeTitle = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500.milliseconds)
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

    private fun loadInitialRecipes() {
        loadSearchRecipe()
    }

    fun onApplyFilters() {
        loadSearchRecipe()
    }

    private fun loadBottomSheetChipsData() {
        refreshCategories()
        refreshRecipeCuisines()
        refreshRecipeDifficultyLvl()
    }

    private fun loadSearchRecipe() {
        viewModelScope.launch {
            _searchResultState.update { it.copy(isLoading = true, errorMsg = null) }
            try {
                val data = repository.getRecipesByFilters(
                    categoryName = selectedCategoryName,
                    cuisineName = selectedCuisinesName,
                    diffLvl = selectedDiffLvlName,
                    title = searchQueryRecipeTitle
                )
                _searchResultState.update { it.copy(recipeList = data.first(), isLoading = false) }
            } catch (e: Exception) {
                _searchResultState.update { it.copy(errorMsg = e.message, isLoading = false) }
            }
        }
    }

    private fun observeCategories() {
        viewModelScope.launch {
            repository.getRecipeCategories().collect { categories ->
                val finalData = listOf(RecipeCategoriesDataModel("-1", "All")) + categories
                _searchFilterState.update { it.copy(recipeCategories = finalData) }
            }
        }
    }

    private fun refreshCategories() {
        viewModelScope.launch {
            _searchFilterState.update {
                it.copy(isCategoriesLoading = true, categoriesErrorMsg = null)
            }
            try {
                repository.refreshCategories()
                _searchFilterState.update { it.copy(isCategoriesLoading = false) }
            } catch (e: Exception) {
                _searchFilterState.update {
                    it.copy(isCategoriesLoading = false, categoriesErrorMsg = e.message)
                }
            }
        }
    }

    private fun observeCuisines() {
        viewModelScope.launch {
            repository.getAllCuisines().collect { cuisines ->
                _searchFilterState.update { it.copy(recipeCuisines = cuisines) }
            }
        }
    }

    private fun observeDiffLvls() {
        viewModelScope.launch {
            repository.getAllRecipeDifficulties().collect { diffLvls ->
                _searchFilterState.update { it.copy(recipeDifficulties = diffLvls) }
            }
        }
    }

    private fun refreshRecipeCuisines() {
        viewModelScope.launch {
            _searchFilterState.update { it.copy(isCuisinesLoading = true, cuisinesErrorMsg = null) }
            try {
                repository.refreshAllCuisines()
                _searchFilterState.update { it.copy(isCuisinesLoading = false) }
            } catch (e: Exception) {
                _searchFilterState.update {
                    it.copy(cuisinesErrorMsg = e.message, isCuisinesLoading = false)
                }
            }
        }
    }

    private fun refreshRecipeDifficultyLvl() {
        viewModelScope.launch {
            _searchFilterState.update { it.copy(isDiffLvlLoading = true, diffLvlErrorMsg = null) }
            try {
                repository.refreshRecipeDifficulties()
                _searchFilterState.update { it.copy(isDiffLvlLoading = false) }
            } catch (e: Exception) {
                _searchFilterState.update {
                    it.copy(diffLvlErrorMsg = e.message, isDiffLvlLoading = false)
                }
            }
        }
    }
}