package com.abid.recipeapp.util

import com.abid.recipeapp.data.model.IngredientModel
import com.abid.recipeapp.data.model.NutritionModel
import com.abid.recipeapp.data.model.RecipeModel

object MockData {
    val mockRecipes = listOf(

        RecipeModel(
            id = "1",
            title = "Classic Margherita Pizza",
            description = "A traditional Italian pizza with fresh mozzarella, tomatoes, and basil",
            image = "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg",
            prepTime = 20,
            cookTime = 15,
            servings = 4,
            difficulty = "Medium",
            category = "Dinner",
            cuisine = "Italian",
            author = "Chef Mario",
            createdAt = "2024-01-15",
            tags = listOf("vegetarian", "italian", "pizza"),
            ingredients = listOf(
                IngredientModel("Pizza dough", "1 lb"),
                IngredientModel("Tomato sauce", "1 cup"),
                IngredientModel("Fresh mozzarella", "8 oz"),
                IngredientModel("Fresh basil leaves", "1/4 cup"),
                IngredientModel("Olive oil", "2 tbsp"),
                IngredientModel("Salt", "to taste")
            ),
            instructions = listOf(
                "Preheat oven to 475°F (245°C)",
                "Roll out pizza dough on a floured surface",
                "Spread tomato sauce evenly over the dough",
                "Add torn mozzarella pieces",
                "Drizzle with olive oil and add salt",
                "Bake for 12-15 minutes until crust is golden",
                "Top with fresh basil leaves and serve"
            ),
            nutrition = NutritionModel(285, 12, 36, 10)
        ),

        RecipeModel(
            id = "2",
            title = "Chicken Tikka Masala",
            description = "Creamy and flavorful Indian curry with tender chicken pieces",
            image = "https://images.pexels.com/photos/2474661/pexels-photo-2474661.jpeg",
            prepTime = 30,
            cookTime = 40,
            servings = 6,
            difficulty = "Medium",
            category = "Dinner",
            cuisine = "Indian",
            author = "Chef Priya",
            createdAt = "2024-01-14",
            tags = listOf("indian", "curry", "chicken"),
            ingredients = listOf(
                IngredientModel("Chicken breast", "2 lbs"),
                IngredientModel("Yogurt", "1 cup"),
                IngredientModel("Heavy cream", "1 cup"),
                IngredientModel("Tomato sauce", "2 cups"),
                IngredientModel("Garam masala", "2 tbsp"),
                IngredientModel("Ginger garlic paste", "2 tbsp"),
                IngredientModel("Onion", "1 large"),
                IngredientModel("Butter", "4 tbsp")
            ),
            instructions = listOf(
                "Marinate chicken in yogurt and spices for 2 hours",
                "Grill or bake chicken until cooked",
                "Sauté onions in butter until golden",
                "Add ginger garlic paste and cook for 2 minutes",
                "Add tomato sauce and spices, simmer for 15 minutes",
                "Stir in cream and cooked chicken",
                "Simmer for 10 minutes and serve with rice"
            ),
            nutrition = NutritionModel(420, 35, 18, 24)
        ),

        RecipeModel(
            id = "3",
            title = "Avocado Toast with Poached Egg",
            description = "Healthy and delicious breakfast with creamy avocado and perfectly poached egg",
            image = "https://images.pexels.com/photos/566566/pexels-photo-566566.jpeg",
            prepTime = 5,
            cookTime = 10,
            servings = 2,
            difficulty = "Easy",
            category = "Breakfast",
            cuisine = "American",
            author = "Chef Emma",
            createdAt = "2024-01-13",
            tags = listOf("breakfast", "healthy", "quick"),
            ingredients = listOf(
                IngredientModel("Whole grain bread", "2 slices"),
                IngredientModel("Avocado", "1 ripe"),
                IngredientModel("Eggs", "2"),
                IngredientModel("Lemon juice", "1 tsp"),
                IngredientModel("Red pepper flakes", "1/4 tsp"),
                IngredientModel("Salt and pepper", "to taste")
            ),
            instructions = listOf(
                "Toast bread slices until golden",
                "Mash avocado with lemon juice, salt, and pepper",
                "Bring water to a gentle simmer for poaching",
                "Crack eggs into simmering water and cook for 3-4 minutes",
                "Spread avocado mixture on toast",
                "Top with poached egg and red pepper flakes"
            ),
            nutrition = NutritionModel(320, 15, 28, 18)
        )

        // I stopped here intentionally.
    )

}