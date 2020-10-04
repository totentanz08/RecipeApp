package com.acts.recipeapp.ui.recipe

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acts.recipeapp.R
import com.acts.recipeapp.data.Recipe
import com.acts.recipeapp.utility.InjectorUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerViewAdapter
    var recipelist = mutableListOf<Recipe>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var wmbPreference = PreferenceManager.getDefaultSharedPreferences(this)
        var isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true)
        var editor = wmbPreference.edit()
        if (isFirstRun) {
            // Code to run once
            populateRecipe()
            editor.putBoolean("FIRSTRUN", false);
            editor.apply()
        }
        initializeRecipeList()
        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        recipe_recycler_view.layoutManager = linearLayoutManager
        adapter = RecyclerViewAdapter(recipelist as ArrayList<Recipe>)
        recipe_recycler_view.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    fun initializeRecipeList(){
        val factory = InjectorUtils.provideRecipeViewModelFactory()
        val viewModel = ViewModelProvider(this, factory).get(RecipeViewModel::class.java)
        viewModel.getRecipes().observe(this, Observer { recipes ->

            recipes.forEach { recipe ->
                recipelist.add(recipe)
            }
        })
    }

    fun populateRecipe(){
        val factory = InjectorUtils.provideRecipeViewModelFactory()
        val viewModel = ViewModelProvider(this, factory).get(RecipeViewModel::class.java)

        //appetizer image
        val appetizer_baos = ByteArrayOutputStream()
        val appetizer_bitmap = BitmapFactory.decodeResource(resources, R.drawable.fried_calamari)
        appetizer_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, appetizer_baos)
        val appetizer_bytes : ByteArray = appetizer_baos.toByteArray()
        val appetizer_string : String = Base64.encodeToString(appetizer_bytes, Base64.DEFAULT)

        //main image
        val main_baos = ByteArrayOutputStream()
        val main_bitmap = BitmapFactory.decodeResource(resources, R.drawable.spaghet)
            main_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, main_baos)
        val main_bytes : ByteArray = main_baos.toByteArray()
        val main_string : String = Base64.encodeToString(main_bytes, Base64.DEFAULT)

        //dessert image
        val dessert_baos = ByteArrayOutputStream()
        val dessert_bitmap = BitmapFactory.decodeResource(resources, R.drawable.chocolate_mousse)
        dessert_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, dessert_baos)
        val dessert_bytes : ByteArray = dessert_baos.toByteArray()
        val dessert_string : String = Base64.encodeToString(dessert_bytes, Base64.DEFAULT)
        val fried_calamari = Recipe(
            getString(R.string.fried_calamari),
            getString(R.string.appetizer_type),
            appetizer_string,
            getString(R.string.appetizer_ingredients),
            getString(R.string.appetizer_steps)
        )
        val spaghet = Recipe(
            getString(R.string.spaghet),
            getString(R.string.main_type),
            main_string,
            getString(R.string.main_ingredients),
            getString(R.string.main_steps)
        )
        val choco_mousse = Recipe(
            getString(R.string.choco_mousse),
            getString(R.string.dessert_type),
            dessert_string,
            getString(R.string.dessert_ingredients),
            getString(R.string.dessert_steps)
        )
        viewModel.addRecipes(fried_calamari)
        viewModel.addRecipes(spaghet)
        viewModel.addRecipes(choco_mousse)

    }

}
