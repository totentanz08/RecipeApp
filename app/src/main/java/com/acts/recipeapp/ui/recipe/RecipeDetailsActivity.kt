package com.acts.recipeapp.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acts.recipeapp.R
import kotlinx.android.synthetic.main.activity_recipe_details.*


class RecipeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        initializeUi()
    }

    private fun initializeUi(){
        val intent = intent
        val recipeBundle = intent.getBundleExtra("recipeBundle")
        val recipeName = recipeBundle!!.getString("recipeName")
        val recipeType = recipeBundle.getString("recipeType")
        //val recipeImgURL = recipeBundle.getString("recipeImgURL") //because I dunno how to pass image I cant get image from the intent
        val recipeIngredients = recipeBundle.getString("recipeIngredients")
        val recipeSteps = recipeBundle.getString("recipeSteps")
        textView_recipeName.text = "Recipe Name: " + recipeName
        textView_recipeType.text = "Recipe Type: " + recipeType
        /*val decodedByte = Base64.decode(recipeImgURL, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        main_recipe_image_view.setImageBitmap(bitmap)*/
        textView_recipeIngredients.text = "Recipe Ingredients: " + recipeIngredients
        textView_recipeSteps.text = "Recipe Steps: " + recipeSteps
    }


}