package com.abid.recipeapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.model.IngredientModel
import com.abid.recipeapp.data.model.NutritionModel
import com.abid.recipeapp.data.model.RecipeDiffLvl
import com.abid.recipeapp.data.model.RecipeModel
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

    private val _uiState = MutableLiveData(CreateRecipeUiState(isLoading = true))
    val uiState: LiveData<CreateRecipeUiState> = _uiState

    private fun updateRecipe(update: RecipeModel.() -> RecipeModel) {
        _uiState.value = _uiState.value?.let { state ->
            state.copy(recipeModel = state.recipeModel.update())
        }
    }

    private fun updateNutrition(update: NutritionModel.() -> NutritionModel) = updateRecipe {
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
        copy(ingredients = ingredients + IngredientModel())
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

    fun loadRecipeCategories() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val data = repository.getRecipeCategories()
                if (data != null) {
                    _uiState.value = _uiState.value?.copy(isLoading = false, categoriesList = data)
                } else {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, errorMsg = "Categories not found")
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    fun loadCuisines() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val data = repository.getCuisines()
                if (data != null) {
                    _uiState.value = _uiState.value?.copy(isLoading = false, cuisinesList = data)
                } else {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, errorMsg = "Cuisines not found")
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    fun loadRecipeDifficulties() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(isLoading = true)
                val data = repository.getRecipeDifficulties()
                if (data != null) {
                    _uiState.value =
                        _uiState.value?.copy(isLoading = false, difficultiesList = data)
                } else {
                    _uiState.value =
                        _uiState.value?.copy(
                            isLoading = false,
                            errorMsg = "Difficulty level not found"
                        )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(isLoading = false, errorMsg = e.message)
            }
        }
    }

    fun saveRecipe(recipeModel: RecipeModel, imageUri: Uri?) {
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
                if (uploadedImageUrl.isNotBlank()) {
                    recipeModel.image = uploadedImageUrl
                } else {
                    recipeModel.image = ""
                }
                _uiState.value =
                    _uiState.value?.copy(
                        uploadedImageUrl = uploadedImageUrl.ifBlank { null }
                    )
                val listener = repository.saveRecipe(recipeModel)
                listener.addOnSuccessListener {
                    _uiState.value =
                        _uiState.value?.copy(
                            isLoading = false,
                            recipeSaveState = RecipeSaveState.Success(recipeModel),
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