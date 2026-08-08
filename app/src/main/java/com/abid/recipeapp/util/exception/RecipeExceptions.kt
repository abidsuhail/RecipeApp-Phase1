package com.abid.recipeapp.util.exception


class InvalidRecipeIdException(message: String = "Recipe id is blank") : Exception(message)
class RecipeNotFoundException(message: String = "Recipe not found") : Exception(message)
class NoInternetException(message: String = "No internet connection") : Exception(message)