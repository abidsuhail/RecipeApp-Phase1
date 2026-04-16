package com.abid.recipeapp.data.repository

import android.net.Uri
import com.abid.recipeapp.data.model.CreateRecipeFormStateModel
import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_CATEGORIES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_CUISINES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_DIFFICULTY_LEVELS
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_INGREDIENTS_INST_TAB
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class RecipeRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    suspend fun getAllRecipes(categoryName: String?): List<RecipeModel> {
        return withContext(Dispatchers.IO) {
            val query = if (categoryName == null) {
                db.collection(RECIPES)
            } else {
                db.collection(RECIPES)
                    .whereEqualTo("category", categoryName)
            }
            val recipes = query
                .get()
                .await()
            recipes.toObjects(RecipeModel::class.java)
        }
    }

    suspend fun getIngredientsTabsItems(): List<IngredientsTabModel> {
        return withContext(Dispatchers.IO) {
            val ingredientsAndInstTabItems =
                db.collection(RECIPE_INGREDIENTS_INST_TAB).get().await()
            ingredientsAndInstTabItems.toObjects(IngredientsTabModel::class.java)
        }
    }

    suspend fun getRecipeCategories(): List<RecipeCategoriesDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCategories = db.collection(RECIPE_CATEGORIES).get().await()
            recipeCategories.toObjects(RecipeCategoriesDataModel::class.java)
        }
    }

    suspend fun getCuisines(): List<RecipeCuisinesDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCuisines = db.collection(RECIPE_CUISINES).get().await()
            recipeCuisines.toObjects(RecipeCuisinesDataModel::class.java)
        }
    }

    suspend fun getRecipeDifficulties(): List<RecipeDiffLvlDataModel> {
        return withContext(Dispatchers.IO) {
            val recipeCuisines = db.collection(RECIPE_DIFFICULTY_LEVELS).get().await()
            recipeCuisines.toObjects(RecipeDiffLvlDataModel::class.java)
        }
    }

    suspend fun getRecipeById(id: String?): RecipeModel? {
        if (id == null) return null
        return withContext(Dispatchers.IO) {
            val recipe = db.collection(RECIPES).document(id).get().await()
            recipe.toObject(RecipeModel::class.java)
        }
    }

    suspend fun saveRecipe(recipeModel: CreateRecipeFormStateModel): Task<Void> {
        return withContext(Dispatchers.IO) {
            val docRef = db.collection(RECIPES).document()
            val recipe = recipeModel.copy(id = docRef.id)
            docRef.set(recipe)
        }
    }

    suspend fun uploadImage(imageUri: Uri): String {
        return suspendCancellableCoroutine { continuation ->
            MediaManager.get()
                .upload(imageUri)
                .unsigned("Abid_android")
                .callback(object : UploadCallback {

                    override fun onStart(requestId: String) {}

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}

                    override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                        val imageUrl = resultData["secure_url"] as? String
                        if (imageUrl != null) {
                            continuation.resume(imageUrl) // ✅ returns the URL
                        } else {
                            continuation.resumeWithException(Exception("Invalid image url"))
                        }
                    }

                    override fun onError(requestId: String?, error: ErrorInfo) {
                        continuation.resumeWithException(Exception(error.description)) // ✅ throws error
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
                })
                .dispatch()
        }
    }
}