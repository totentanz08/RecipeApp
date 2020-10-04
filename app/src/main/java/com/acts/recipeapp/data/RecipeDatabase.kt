package com.acts.recipeapp.data

class RecipeDatabase {
    var recipeDao = RecipeDao()
        private set

    companion object{
        @Volatile private var instance : RecipeDatabase? = null

        fun getInstance() =
                instance ?: synchronized(this){
                    instance ?: RecipeDatabase().also{instance = it}
                }
    }
}