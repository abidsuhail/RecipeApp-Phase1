package com.abid.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.FavUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavScreenViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FavUiState(isLoading = true))
    val state: StateFlow<FavUiState> = _state.asStateFlow()


    init {
        observeFavRecipes()
        refreshFavRecipes()
    }

    private fun refreshFavRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                repository.refreshFavRecipes()
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(errorMsg = e.message, isLoading = false) }
            }
        }
    }

    private fun observeFavRecipes() {
        viewModelScope.launch {
            repository.getAllFavRecipes().collect { favs ->
                _state.update { it.copy(favRecipeList = favs) }
            }
        }
    }


}