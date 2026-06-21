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


    fun loadAllFavList() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isLoading = true)
                val data = repository.getAllFavRecipesList()
                _state.value = _state.value?.copy(favRecipeList = data, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value?.copy(errorMsg = e.message, isLoading = false)
            }
        }
    }

}