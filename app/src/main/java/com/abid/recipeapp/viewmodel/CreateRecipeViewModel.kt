package com.abid.recipeapp.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.model.CreateRecipeFormStateModel
import com.abid.recipeapp.data.model.IngredientFormStateModel
import com.abid.recipeapp.data.model.NutritionFormStateModel
import com.abid.recipeapp.data.model.RecipeDiffLvl
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.ui.state.CreateRecipeUiState
import com.abid.recipeapp.ui.state.RecipeSaveState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateRecipeUiState(isLoading = true))
    val uiState: StateFlow<CreateRecipeUiState> = _uiState.asStateFlow()

    init {
        observeCategories()
        refreshCategories()

        observeCuisines()
        refreshCuisines()

        observeDiffLvls()
        refreshDifficulties()
    }


    private fun updateRecipe(update: CreateRecipeFormStateModel.() -> CreateRecipeFormStateModel) {
        _uiState.update {
            it.copy(recipeModel = it.recipeModel.update())
        }
    }

    private fun updateNutrition(update: NutritionFormStateModel.() -> NutritionFormStateModel) =
        updateRecipe {
            copy(nutrition = nutrition.update())
        }

    fun onTitleChange(value: String) = updateRecipe { copy(title = value) }
    fun onDescriptionChange(value: String) = updateRecipe { copy(description = value) }
    fun onCategoryChange(value: String) = updateRecipe { copy(category = value) }
    fun onCuisineChange(value: String) = updateRecipe { copy(cuisine = value) }

    fun onPrepTimeChange(value: String) = updateRecipe { copy(prepTime = value.toIntOrNull() ?: 0) }
    fun onCookTimeChange(value: String) = updateRecipe { copy(cookTime = value.toIntOrNull() ?: 0) }
    fun onServingsChange(value: String) = updateRecipe { copy(servings = value.toIntOrNull() ?: 0) }

    fun onDifficultyChange(value: RecipeDiffLvl) = updateRecipe { copy(difficulty = value) }


    fun onCaloriesChange(value: String) =
        updateNutrition { copy(calories = value.toIntOrNull() ?: 0) }

    fun onProteinChange(value: String) =
        updateNutrition { copy(protein = value.toIntOrNull() ?: 0) }

    fun onCarbsChange(value: String) = updateNutrition { copy(carbs = value.toIntOrNull() ?: 0) }
    fun onFatChange(value: String) = updateNutrition { copy(fat = value.toIntOrNull() ?: 0) }
    fun onInstructionAdd() = updateRecipe {
        copy(instructions = instructions + "")
    }

    fun onInstructionRemove(index: Int) = updateRecipe {
        copy(instructions = instructions.toMutableList().also { it.removeAt(index) })
    }

    fun onInstructionChange(index: Int, value: String) = updateRecipe {
        copy(instructions = instructions.toMutableList().also { it[index] = value })
    }

    fun onIngredientAdd() = updateRecipe {
        copy(ingredients = ingredients + IngredientFormStateModel())
    }

    fun onIngredientRemove(index: Int) = updateRecipe {
        copy(ingredients = ingredients.toMutableList().also { it.removeAt(index) })
    }

    fun onIngredientItemChange(index: Int, value: String) = updateRecipe {
        copy(ingredients = ingredients.toMutableList().also {
            it[index] = it[index].copy(item = value)
        })
    }

    fun onIngredientAmountChange(index: Int, value: String) = updateRecipe {
        copy(ingredients = ingredients.toMutableList().also {
            it[index] = it[index].copy(amount = value)
        })
    }

    private fun refreshCategories() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMsg = null) }
                repository.refreshCategories()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMsg = e.message) }
            }
        }
    }

    private fun observeCategories() {
        viewModelScope.launch {
            repository.getRecipeCategories().collect { categories ->
                _uiState.update { it.copy(categoriesList = categories) }
            }
        }
    }

    private fun observeCuisines() {
        viewModelScope.launch {
            repository.getAllCuisines().collect { cuisines ->
                _uiState.update { it.copy(cuisinesList = cuisines) }
            }
        }
    }

    private fun observeDiffLvls() {
        viewModelScope.launch {
            repository.getAllRecipeDifficulties().collect { difficulties ->
                _uiState.update { it.copy(difficultiesList = difficulties) }
            }
        }
    }

    private fun refreshCuisines() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.refreshAllCuisines()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMsg = e.message) }
            }
        }
    }

    private fun refreshDifficulties() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.refreshRecipeDifficulties()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMsg = e.message) }
            }
        }
    }

    fun saveRecipe(recipeModel: CreateRecipeFormStateModel, imageUri: Uri?) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                if (imageUri == null) {
                    _uiState.update {
                        it.copy(isLoading = false, errorMsg = "Image uri is null")
                    }
                    return@launch
                }
                repository.saveRecipe(recipeModel, imageUri)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recipeSaveState = RecipeSaveState.Success(recipeModel)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMsg = e.message) }
            }
        }
    }

}