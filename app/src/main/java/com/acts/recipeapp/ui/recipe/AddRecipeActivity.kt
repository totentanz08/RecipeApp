package com.acts.recipeapp.ui.recipe

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.acts.recipeapp.R
import com.acts.recipeapp.data.Recipe
import com.acts.recipeapp.utility.InjectorUtils
import kotlinx.android.synthetic.main.activity_add_recipe.*
import java.io.ByteArrayOutputStream

class AddRecipeActivity : AppCompatActivity() {
    private var imagebase64 = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        initializeUi()
    }
    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000;
        //Permission code
        private const val PERMISSION_CODE = 1001;
    }

    private fun initializeUi(){
        val factory = InjectorUtils.provideRecipeViewModelFactory()
        val viewModel = ViewModelProvider(this,factory).get(RecipeViewModel::class.java)

        //populating the spinner

        val recipetypeSpinner: Spinner = findViewById(R.id.spinner_recipeType)

        //creating arrayadapter

        ArrayAdapter.createFromResource(
            this,
            R.array.recipetypes_array,
            android.R.layout.simple_spinner_item
        ).also {adapter->
            //Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))
            //applying adapter to spinner
            recipetypeSpinner.adapter = adapter
        }
        recipetypeSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val recipetype = recipetypeSpinner.getItemAtPosition(position).toString()
              addrecipe_TextView_recipeType.text = recipetype

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                addrecipe_TextView_recipeType.text = "Please select a recipe type"
            }

        }

        btn_img_pick.setOnClickListener{
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
        fab_addRecipe.setOnClickListener{
            val recipe = Recipe(editText_recipeName.text.toString(),
                addrecipe_TextView_recipeType.text.toString(),
                imagebase64,
                editText_recipeIngredients.text.toString(),
                editText_recipeSteps.text.toString())
            viewModel.addRecipes(recipe)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
    private fun pickImageFromGallery() {
        //Intent to pick image
        val imageintent = Intent(Intent.ACTION_PICK)
        imageintent.type = "image/*"
        startActivityForResult(imageintent, IMAGE_PICK_CODE)
    }
    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            recipe_image_view.setImageURI(data?.data)
            val uri : Uri? = data?.data;
            val byteArrayOutputStream = ByteArrayOutputStream()
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
            val b = byteArrayOutputStream.toByteArray()
            imagebase64 = Base64.encodeToString(b,Base64.DEFAULT)
        }
    }




}