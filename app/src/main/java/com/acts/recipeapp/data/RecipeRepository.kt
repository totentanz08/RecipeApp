package com.acts.recipeapp.data

class RecipeRepository(private val recipeDao: RecipeDao) {

    fun addRecipe(recipe:Recipe){
        recipeDao.addRecipe(recipe)
    }

    fun getRecipes() = recipeDao.getRecipe()

    companion object{
        @Volatile private var instance: RecipeRepository?= null

        fun getInstance(recipeDao: RecipeDao)=
            instance ?: synchronized(this){
                instance?: RecipeRepository(recipeDao).also{instance = it}
            }
    }
}