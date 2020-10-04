package com.acts.recipeapp.utility

import com.acts.recipeapp.data.RecipeDatabase
import com.acts.recipeapp.data.RecipeRepository
import com.acts.recipeapp.ui.recipe.RecipeViewModelFactory

object InjectorUtils{

    fun provideRecipeViewModelFactory(): RecipeViewModelFactory {
        val recipeRepository = RecipeRepository.getInstance(RecipeDatabase.getInstance().recipeDao)
        return RecipeViewModelFactory(recipeRepository)
    }
}