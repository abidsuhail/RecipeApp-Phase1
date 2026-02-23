package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository // assume you have one
) : ViewModel() {

    private val _allRecipes = MutableLiveData<List<RecipeModel>>(emptyList())
    val allRecipes: LiveData<List<RecipeModel>> = _allRecipes
    private val _foundRecipe = MutableLiveData<RecipeModel>(null)
    val foundRecipe: LiveData<RecipeModel> = _foundRecipe

    fun getAllRecipes() {
        viewModelScope.launch {
            _allRecipes.value = repository.getAllRecipes()
        }
    }

    fun loadRecipe(recipeId: String?) {
        viewModelScope.launch {
            _foundRecipe.value = repository.getRecipeById(recipeId)
        }
    }
}