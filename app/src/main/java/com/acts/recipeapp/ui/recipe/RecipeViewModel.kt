package com.acts.recipeapp.ui.recipe

import androidx.lifecycle.ViewModel
import com.acts.recipeapp.data.Recipe
import com.acts.recipeapp.data.RecipeRepository

class RecipeViewModel(private val recipeRepository: RecipeRepository): ViewModel() {

    fun getRecipes() = recipeRepository.getRecipes()
    fun addRecipes(recipe: Recipe) = recipeRepository.addRecipe(recipe)

}