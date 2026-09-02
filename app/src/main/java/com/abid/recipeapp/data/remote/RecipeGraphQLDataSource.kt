package com.abid.recipeapp.data.remote


import android.net.Uri
import com.abid.recipeapp.AddFavoriteMutation
import com.abid.recipeapp.CreateRecipeMutation
import com.abid.recipeapp.FavoritesQuery
import com.abid.recipeapp.IsFavoriteQuery
import com.abid.recipeapp.RecipeLookupsQuery
import com.abid.recipeapp.RecipesQuery
import com.abid.recipeapp.RemoveFavoriteMutation
import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.type.IngredientInput
import com.abid.recipeapp.type.NutritionInput
import com.abid.recipeapp.type.RecipeInput
import com.abid.recipeapp.util.datamapper.graphql.toRecipeModel
import com.abid.recipeapp.util.toRecipeDiffLvl
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RecipeGraphQLDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val imageUploader: CloudinaryImageUploader
) {

    suspend fun getAllRecipes(): List<RecipeModel> = withContext(Dispatchers.IO) {
        val response = apolloClient.query(
            RecipesQuery()
        ).execute()

        if (response.hasErrors()) {
            throw GraphQLApiException(response.errors?.firstOrNull()?.message ?: "Unknown error")
        }

        response.data?.recipes?.map { it.toRecipeModel() } ?: emptyList()
    }

    suspend fun getIngredientsTabsItems(): List<IngredientsTabModel> = withContext(Dispatchers.IO) {
        val response = apolloClient.query(RecipeLookupsQuery()).execute()
        if (response.hasErrors()) {
            throw GraphQLApiException(
                response.errors?.firstOrNull()?.message ?: "Unknown error"
            )
        }
        response.data?.ingredientInstructionTabs?.map {
            IngredientsTabModel(id = it.id, label = it.label)
        } ?: emptyList()
    }

    suspend fun getRecipeCategories(): List<RecipeCategoriesDataModel> =
        withContext(Dispatchers.IO) {
            val response = apolloClient.query(RecipeLookupsQuery()).execute()
            if (response.hasErrors()) {
                throw GraphQLApiException(
                    response.errors?.firstOrNull()?.message ?: "Unknown error"
                )
            }
            response.data?.recipeCategories?.map {
                RecipeCategoriesDataModel(id = it.id, label = it.label)
            } ?: emptyList()
        }

    suspend fun getCuisines(): List<RecipeCuisinesDataModel> = withContext(Dispatchers.IO) {
        val response = apolloClient.query(RecipeLookupsQuery()).execute()
        if (response.hasErrors()) {
            throw GraphQLApiException(
                response.errors?.firstOrNull()?.message ?: "Unknown error"
            )
        }
        response.data?.recipeCuisines?.map {
            RecipeCuisinesDataModel(id = it.id, label = it.label)
        } ?: emptyList()
    }

    suspend fun getRecipeDifficulties(): List<RecipeDiffLvlDataModel> =
        withContext(Dispatchers.IO) {
            val response = apolloClient.query(RecipeLookupsQuery()).execute()
            if (response.hasErrors()) {
                throw GraphQLApiException(
                    response.errors?.firstOrNull()?.message ?: "Unknown error"
                )
            }
            response.data?.difficultyLevels?.map {
                RecipeDiffLvlDataModel(id = it.id, label = it.label.toRecipeDiffLvl())
            } ?: emptyList()
        }

    suspend fun saveRecipe(recipeModel: RecipeModel): Boolean = withContext(Dispatchers.IO) {
        val input = RecipeInput(
            title = recipeModel.title,
            description = Optional.presentIfNotNull(recipeModel.description),
            category = Optional.presentIfNotNull(recipeModel.category),
            cuisine = Optional.presentIfNotNull(recipeModel.cuisine),
            difficulty = Optional.presentIfNotNull(recipeModel.difficulty.name),
            author = Optional.presentIfNotNull(recipeModel.author),
            image = Optional.presentIfNotNull(recipeModel.image),
            prepTime = Optional.presentIfNotNull(recipeModel.prepTime),
            cookTime = Optional.presentIfNotNull(recipeModel.cookTime),
            servings = Optional.presentIfNotNull(recipeModel.servings),
            tags = Optional.presentIfNotNull(recipeModel.tags),
            instructions = Optional.presentIfNotNull(recipeModel.instructions),
            ingredients = Optional.presentIfNotNull(
                recipeModel.ingredients?.map {
                    IngredientInput(
                        item = it.item,
                        amount = Optional.presentIfNotNull(it.amount)
                    )
                }
            ),
            nutrition = Optional.presentIfNotNull(
                recipeModel.nutrition?.let {
                    NutritionInput(
                        calories = Optional.presentIfNotNull(it.calories),
                        protein = Optional.presentIfNotNull(it.protein),
                        fat = Optional.presentIfNotNull(it.fat),
                        carbs = Optional.presentIfNotNull(it.carbs),
                    )
                }
            ),
        )

        val response = apolloClient.mutation(CreateRecipeMutation(input)).execute()
        if (response.hasErrors()) {
            throw GraphQLApiException(response.errors?.firstOrNull()?.message ?: "Unknown error")
        }
        true
    }

    suspend fun saveFavRemote(recipeId: String) = withContext(Dispatchers.IO) {
        val response = apolloClient.mutation(AddFavoriteMutation(recipeId)).execute()
        if (response.hasErrors()) {
            throw GraphQLApiException(response.errors?.firstOrNull()?.message ?: "Unknown error")
        }
    }

    suspend fun removeFavRemote(recipeId: String) = withContext(Dispatchers.IO) {
        val response = apolloClient.mutation(RemoveFavoriteMutation(recipeId)).execute()
        if (response.hasErrors()) {
            throw GraphQLApiException(response.errors?.firstOrNull()?.message ?: "Unknown error")
        }
    }

    suspend fun getAllFavRecipesList(): List<RecipeModel> = withContext(Dispatchers.IO) {
        // one request instead of a manual whereIn join — the `recipe` nested
        // field on Favorite fetches each recipe server-side
        val response = apolloClient.query(FavoritesQuery()).execute()
        if (response.hasErrors()) {
            throw GraphQLApiException(response.errors?.firstOrNull()?.message ?: "Unknown error")
        }
        response.data?.favorites
            ?.mapNotNull { it.recipe }
            ?.map { it.toRecipeModel() }
            ?: emptyList()
    }

    suspend fun uploadImage(imageUri: Uri): String {
        return imageUploader.uploadImage(imageUri)
    }

    suspend fun isFav(recipeId: String): Boolean = withContext(Dispatchers.IO) {
        val response = apolloClient.query(IsFavoriteQuery(recipeId)).execute()
        if (response.hasErrors()) {
            throw GraphQLApiException(response.errors?.firstOrNull()?.message ?: "Unknown error")
        }
        response.data?.isFavorite ?: false
    }

}

class GraphQLApiException(message: String) : Exception(message)

