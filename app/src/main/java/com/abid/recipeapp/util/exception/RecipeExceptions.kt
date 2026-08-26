package com.abid.recipeapp.util.exception

import com.abid.recipeapp.util.Constants


class InvalidRecipeIdException(message: String = Constants.ExceptionMessages.INVALID_RECIPE_ID) :
    Exception(message)

class RecipeNotFoundException(message: String = Constants.ExceptionMessages.RECIPE_NOT_FOUND) :
    Exception(message)

class NoInternetException(message: String = Constants.ExceptionMessages.NO_INTERNET) :
    Exception(message)