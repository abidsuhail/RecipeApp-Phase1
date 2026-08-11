package com.abid.recipeapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    init {
        observeCategories()
        refreshCategories()

        observeCuisines()
        refreshCuisines()

        observeDiffLvls()
        refreshDifficulties()
    }

    private val _uiState = MutableLiveData(CreateRecipeUiState(isLoading = true))
    val uiState: LiveData<CreateRecipeUiState> = _uiState

    private fun updateRecipe(update: CreateRecipeFormStateModel.() -> CreateRecipeFormStateModel) {
        _uiState.value = _uiState.value?.let { state ->
            state.copy(recipeModel = state.recipeModel.update())
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
            _uiState.value = _uiState.value?.copy(isLoading = true, errorMsg = null)
            try {
                repository.refreshCategories()
                _uiState.value = _uiState.value?.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    private fun observeCategories() {
        viewModelScope.launch {
            repository.getRecipeCategories().collect { categories ->
                _uiState.value = _uiState.value?.copy(categoriesList = categories)
            }
        }
    }

    private fun observeCuisines() {
        viewModelScope.launch {
            repository.getAllCuisines().collect { categories ->
                _uiState.value = _uiState.value?.copy(cuisinesList = categories)
            }
        }
    }

    private fun observeDiffLvls() {
        viewModelScope.launch {
            repository.getAllRecipeDifficulties().collect { categories ->
                _uiState.value = _uiState.value?.copy(difficultiesList = categories)
            }
        }
    }

    private fun refreshCuisines() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                repository.refreshAllCuisines()
                _uiState.value = _uiState.value?.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    private fun refreshDifficulties() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                repository.refreshRecipeDifficulties()
                _uiState.value = _uiState.value?.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    fun saveRecipe(recipeModel: CreateRecipeFormStateModel, imageUri: Uri?) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                if (imageUri == null) {
                    _uiState.value =
                        _uiState.value?.copy(
                            isLoading = false,
                            errorMsg = "Image uri is null"
                        )
                    return@launch
                }
                val uploadedImageUrl = repository.uploadImage(imageUri)
                val updatedRecipeModel = recipeModel.copy(
                    image = uploadedImageUrl.ifBlank { "" }
                )
                _uiState.value =
                    _uiState.value?.copy(
                        uploadedImageUrl = uploadedImageUrl.ifBlank { null }
                    )
                val listener = repository.saveRecipe(updatedRecipeModel)
                listener.addOnSuccessListener {
                    _uiState.value =
                        _uiState.value?.copy(
                            isLoading = false,
                            recipeSaveState = RecipeSaveState.Success(updatedRecipeModel),
                        )
                }.addOnFailureListener {
                    _uiState.value =
                        _uiState.value?.copy(
                            isLoading = false,
                            errorMsg = "Unable to save recipe"
                        )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

}