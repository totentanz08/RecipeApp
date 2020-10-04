package com.acts.recipeapp.ui.recipe

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acts.recipeapp.R
import com.acts.recipeapp.data.Recipe
import com.acts.recipeapp.utility.inflate
import kotlinx.android.synthetic.main.recipes.view.*
import java.io.ByteArrayOutputStream


class RecyclerViewAdapter(private val recipes: ArrayList<Recipe>): RecyclerView.Adapter<RecyclerViewAdapter.RecipeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.RecipeHolder {
        val inflatedView = parent.inflate(R.layout.recipes, false)
        return RecipeHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.RecipeHolder, position: Int) {
        val itemRecipe = recipes[position]
        holder.bindRecipe(itemRecipe)
    }

    override fun getItemCount() = recipes.size

    class RecipeHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{
        private var view:View = v
        private lateinit var recipe:Recipe
        init{
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val showRecipeIntent = Intent(context, RecipeDetailsActivity::class.java)
            val recipeBundle = Bundle()
            recipeBundle.putString("recipeName", recipe.recipeName)
            recipeBundle.putString("recipeType", recipe.recipeType)
            //recipeBundle.putString("recipeImgURL", recipe?.recipeImgURL) //this gives failed binder transaction because size too big, I have no idea how to pass image :(
            recipeBundle.putString("recipeIngredients", recipe.recipeIngredients)
            recipeBundle.putString("recipeSteps", recipe.recipeSteps)

            showRecipeIntent.putExtra("recipeBundle", recipeBundle)
            context.startActivity(showRecipeIntent)
        }
        companion object{
            private val RECIPE_KEY = "RECIPE"
        }
        fun bindRecipe(recipe: Recipe){
            this.recipe = recipe

            val baos = ByteArrayOutputStream()
            val bytes : ByteArray = baos.toByteArray()

            val decodedByte = Base64.decode(recipe.recipeImgURL, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)

            view.rv_recipe_icon.setImageBitmap(bitmap)
            view.rv_recipe_title.text = recipe.recipeName
            view.rv_recipe_type.text = recipe.recipeType
        }


    }
}