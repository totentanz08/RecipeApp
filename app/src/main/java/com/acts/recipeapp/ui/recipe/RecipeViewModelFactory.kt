package com.acts.recipeapp.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.acts.recipeapp.data.RecipeRepository

class RecipeViewModelFactory (private val recipeRepository:RecipeRepository) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecipeViewModel(recipeRepository) as T
    }
}