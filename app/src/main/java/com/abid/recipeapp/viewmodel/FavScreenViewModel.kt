package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.FavUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavScreenViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableLiveData(FavUiState(isLoading = true))
    val state: LiveData<FavUiState> = _state


    init {
        observeFavRecipes()
        refreshFavRecipes()
    }

    private fun refreshFavRecipes() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)
            try {
                repository.refreshFavRecipes()
                _state.value = _state.value?.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value?.copy(errorMsg = e.message, isLoading = false)
            }
        }
    }

    private fun observeFavRecipes() {
        viewModelScope.launch {
            repository.getAllFavRecipes().collect { favs ->
                _state.value = _state.value?.copy(favRecipeList = favs)
            }
        }
    }


}