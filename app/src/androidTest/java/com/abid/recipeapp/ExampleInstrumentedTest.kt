package com.abid.recipeapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abid.recipeapp.data.model.RecipeDiffLvl
import com.abid.recipeapp.data.model.RecipeModel
import com.abid.recipeapp.ui.components.RecipeList
import com.abid.recipeapp.ui.screens.RecipeListHeader
import com.abid.recipeapp.util.Constants
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val fakeRecipes = listOf(
        RecipeModel(
            id = "1",
            title = "Sandwich",
            description = "dasdas",
            prepTime = 3,
            servings = 3,
            difficulty = RecipeDiffLvl.MEDIUM
        ),
        RecipeModel(
            id = "2",
            title = "Chole",
            description = "aaa",
            prepTime = 3,
            servings = 1,
            difficulty = RecipeDiffLvl.EASY
        )
    )

    @Test
    fun homeScreen_displaysTitle() {
        composeTestRule.setContent {
            RecipeListHeader(onClickAddRecipe = {})   // pass a no-op lambda for the click handler
        }
        composeTestRule
            .onNodeWithText(Constants.DISCOVER_RECIPES)
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysRecipeCards_whenDataLoaded() {
        // given a fake list of RecipeModel, verify recipe titles/images render
        composeTestRule.setContent {
            RecipeList(fakeRecipes) { }
        }
        composeTestRule.onNodeWithText("Sandwich").assertIsDisplayed()
        composeTestRule.onNodeWithText("Chole").assertIsDisplayed()
    }

    /*    @Test
        fun homeScreen_showsLoadingIndicator_whenLoading() {
            // isRecipesLoading = true → loading UI shows, recipe list hidden
        }

        @Test
        fun homeScreen_showsEmptyState_whenNoRecipes() {
            // recipeList is empty, not loading → appropriate empty message shows

            composeTestRule.setContent {
                RecipeList(emptyList()) { }
            }
            composeTestRule.onNodeWithText(Constants.RECIPE_NOT_FOUND_ERROR_MSG).assertIsDisplayed()
        }

        @Test
        fun homeScreen_categoryChip_click_filtersRecipes() {
            // tapping "Dinner" chip triggers the filter/search function
        }

        @Test
        fun noInternetBanner_showsOnlyWhenDisconnected() {
            // already covered in the earlier example
        }*/
}