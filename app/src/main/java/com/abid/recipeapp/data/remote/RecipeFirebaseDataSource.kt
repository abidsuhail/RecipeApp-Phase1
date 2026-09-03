package com.abid.recipeapp.data.remote

import android.net.Uri
import com.abid.recipeapp.data.model.FavRecipeModel
import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.util.Constants.FirebaseCollections.FAV
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_CATEGORIES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_CUISINES
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_DIFFICULTY_LEVELS
import com.abid.recipeapp.util.Constants.FirebaseCollections.RECIPE_INGREDIENTS_INST_TAB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume


class RecipeFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val imageUploader: CloudinaryImageUploader
) {

    suspend fun getAllRecipes(): List<RecipeModel> = withContext(Dispatchers.IO) {
        db.collection(RECIPES).get().await().toObjects(RecipeModel::class.java)
    }


    suspend fun getIngredientsTabsItems(): List<IngredientsTabModel> {
        return withContext(Dispatchers.IO) {
            val ingredientsAndInstTabItems =
                db.collection(RECIPE_INGREDIENTS_INST_TAB).get().await()
            return@withContext ingredientsAndInstTabItems.toObjects(IngredientsTabModel::class.java)
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


    suspend fun saveRecipe(recipeModel: RecipeModel): Boolean {
        return withContext(Dispatchers.IO) {
            val docRef = db.collection(RECIPES).document(recipeModel.id)
            val listener = docRef.set(recipeModel)
            val isSuccess = suspendCancellableCoroutine { continuation ->
                listener.addOnSuccessListener {
                    continuation.resume(true)
                }.addOnFailureListener {
                    continuation.resume(false)
                }
            }
            isSuccess
        }
    }

    suspend fun saveFavRemote(recipeId: String) {
        withContext(Dispatchers.IO) {
            db.collection(FAV).document(recipeId).set(FavRecipeModel(recipeId)).await()
        }
    }

    suspend fun removeFavRemote(recipeId: String) {
        withContext(Dispatchers.IO) {
            db.collection(FAV).document(recipeId).delete().await()
        }
    }

    suspend fun getAllFavRecipesList(): List<RecipeModel> {
        return withContext(Dispatchers.IO) {
            val favIds = db.collection(FAV)
                .get()
                .await()
                .toObjects(FavRecipeModel::class.java)
                .map { it.recipeId }
            if (favIds.isEmpty()) return@withContext emptyList()
            db.collection(RECIPES)
                .whereIn("id", favIds)
                .get()
                .await()
                .toObjects(RecipeModel::class.java)
        }
    }

    suspend fun isFav(recipeId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val docRef = db.collection(FAV).document(recipeId)
            docRef.get().await().exists()
        }
    }

    suspend fun uploadImage(imageUri: Uri): String {
        return imageUploader.uploadImage(imageUri)
    }

    suspend fun updateRecipeImageUrl(recipeId: String, imageUrl: String) {
        db.collection(RECIPES).document(recipeId)
            .update("image", imageUrl)
            .await()
    }
}