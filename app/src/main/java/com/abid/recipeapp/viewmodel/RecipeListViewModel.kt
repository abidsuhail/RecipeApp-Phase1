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

    private val _uiState = MutableLiveData<RecipeListUiState>(RecipeListUiState.Loading)
    val uiState: LiveData<RecipeListUiState> = _uiState

    fun loadAllRecipes() {
        viewModelScope.launch {
            try {
                _uiState.value = RecipeListUiState.Loading
                val data = repository.getAllRecipes()
                _uiState.value = RecipeListUiState.Success(data)
            } catch (e: Exception) {
                _uiState.value =
                    RecipeListUiState.Error(e.message ?: "There are no recipes available!")
            }
        }
    }


}