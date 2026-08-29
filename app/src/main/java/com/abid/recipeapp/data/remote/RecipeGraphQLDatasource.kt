package com.abid.recipeapp.data.remote


import com.abid.recipeapp.AddFavoriteMutation
import com.abid.recipeapp.CreateRecipeMutation
import com.abid.recipeapp.FavoritesQuery
import com.abid.recipeapp.IsFavoriteQuery
import com.abid.recipeapp.RecipeLookupsQuery
import com.abid.recipeapp.RecipesQuery
import com.abid.recipeapp.RemoveFavoriteMutation
import com.abid.recipeapp.UpdateRecipeMutation
import com.abid.recipeapp.data.model.IngredientsTabModel
import com.abid.recipeapp.data.model.RecipeCategoriesDataModel
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.data.model.RecipeDiffLvlDataModel
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.type.IngredientInput
import com.abid.recipeapp.type.NutritionInput
import com.abid.recipeapp.type.RecipeInput
import com.abid.recipeapp.util.toRecipeDiffLvl
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Same public API as the Firestore-based RecipeRemoteDataSource, now backed
 * by Apollo Kotlin's generated, type-safe query/mutation classes instead of
 * direct Firestore calls. Requires the .graphql operation files (Recipes,
 * Recipe, RecipeLookups, Favorites, IsFavorite, CreateRecipe, UpdateRecipe,
 * AddFavorite, RemoveFavorite) + schema.graphqls to be present so Apollo's
 * Gradle plugin can generate RecipesQuery, AddFavoriteMutation, etc.
 */
class RecipeGraphQLDatasource @Inject constructor(
    private val apolloClient: ApolloClient,
) {

    suspend fun getAllRecipes(): List<RecipeModel> = withContext(Dispatchers.IO) {
        val response = apolloClient.query(
            RecipesQuery(
                category = Optional.absent(),
                cuisine = Optional.absent(),
                difficulty = Optional.absent(),
            )
        ).execute()

        if (response.hasErrors()) {
            throw GraphQLApiException(response.errors?.firstOrNull()?.message ?: "Unknown error")
        }

        response.data?.recipes?.map { it.toRecipeModel() } ?: emptyList()
    }

    suspend fun getIngredientsTabsItems(): List<IngredientsTabModel> = withContext(Dispatchers.IO) {
        val response = apolloClient.query(RecipeLookupsQuery()).execute()
        response.data?.ingredientInstructionTabs?.map {
            IngredientsTabModel(id = it.id, label = it.label)
        } ?: emptyList()
    }

    suspend fun getRecipeCategories(): List<RecipeCategoriesDataModel> =
        withContext(Dispatchers.IO) {
            val response = apolloClient.query(RecipeLookupsQuery()).execute()
            response.data?.recipeCategories?.map {
                RecipeCategoriesDataModel(id = it.id, label = it.label)
            } ?: emptyList()
        }

    suspend fun getCuisines(): List<RecipeCuisinesDataModel> = withContext(Dispatchers.IO) {
        val response = apolloClient.query(RecipeLookupsQuery()).execute()
        response.data?.recipeCuisines?.map {
            RecipeCuisinesDataModel(id = it.id, label = it.label)
        } ?: emptyList()
    }

    suspend fun getRecipeDifficulties(): List<RecipeDiffLvlDataModel> =
        withContext(Dispatchers.IO) {
            val response = apolloClient.query(RecipeLookupsQuery()).execute()
            response.data?.difficultyLevels?.map {
                RecipeDiffLvlDataModel(id = it.id, label = it.label.toRecipeDiffLvl())
            } ?: emptyList()
        }

    suspend fun saveRecipe(recipeModel: RecipeModel): Boolean = withContext(Dispatchers.IO) {
        try {
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
            !response.hasErrors()
        } catch (e: ApolloException) {
            false
        }
    }

    suspend fun saveFavRemote(recipeId: String) = withContext(Dispatchers.IO) {
        apolloClient.mutation(AddFavoriteMutation(recipeId)).execute()
        Unit
    }

    suspend fun removeFavRemote(recipeId: String) = withContext(Dispatchers.IO) {
        apolloClient.mutation(RemoveFavoriteMutation(recipeId)).execute()
        Unit
    }

    suspend fun getAllFavRecipesList(): List<RecipeModel> = withContext(Dispatchers.IO) {
        // one request instead of a manual whereIn join — the `recipe` nested
        // field on Favorite fetches each recipe server-side
        val response = apolloClient.query(FavoritesQuery()).execute()
        response.data?.favorites
            ?.mapNotNull { it.recipe }
            ?.map { it.toRecipeModel() }
            ?: emptyList()
    }

    suspend fun isFav(recipeId: String): Boolean = withContext(Dispatchers.IO) {
        val response = apolloClient.query(IsFavoriteQuery(recipeId)).execute()
        response.data?.isFavorite ?: false
    }

    // Image upload stays exactly as-is — Cloudinary upload has nothing to do
    // with Firestore or GraphQL, so keep your existing implementation here.
    suspend fun uploadImage(imageUri: android.net.Uri): String {
        throw NotImplementedError("Keep your existing Cloudinary upload code here")
    }

    suspend fun updateRecipeImageUrl(recipeId: String, imageUrl: String) =
        withContext(Dispatchers.IO) {
            // updateRecipe merges fields server-side, so sending just `image`
            // inside input won't wipe out the recipe's other fields.
            val input = RecipeInput(
                title = "", // required by RecipeInput's title: String! — see note below
                image = Optional.present(imageUrl),
            )
            apolloClient.mutation(UpdateRecipeMutation(recipeId, input)).execute()
            Unit
        }
}

class GraphQLApiException(message: String) : Exception(message)

// Helper to map the generated `RecipesQuery.Recipe` (or `Recipe.Recipe` from
// the single-recipe query) shape onto your existing RecipeModel. Field names
// will differ slightly depending on which generated class you're mapping
// from — adjust to match whatever Apollo actually generates for your project.
private fun Any.toRecipeModel(): RecipeModel {
    throw NotImplementedError(
        "Map the generated Recipe fragment's fields onto RecipeModel here " +
                "— exact type depends on what Apollo's codegen names it in your project."
    )
}
