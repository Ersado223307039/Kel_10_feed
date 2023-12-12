package com.example.kel_10_feed

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.kel_10_feed.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    private var foodName:String?=null
    private var foodimage:String?=null
    private var foodDescription:String?=null
    private var foodIngredients:String?=null
    private var foodPrice:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodName=intent.getStringExtra("menuItemName")
        foodDescription=intent.getStringExtra("menuItemDescription")
        foodIngredients=intent.getStringExtra("menuItemIngredient")
        foodPrice=intent.getStringExtra("menuItemPrice")
        foodimage=intent.getStringExtra("menuItemImage")

        with(binding){
            DetailFoodName.text=foodName
            descriptionTeksView.text=foodDescription
            ingredientsTeksView.text=foodIngredients
            Glide.with(this@DetailActivity).load(Uri.parse(foodimage)).into(detailFoodImage)

        }


        binding.imageButton.setOnClickListener {
            finish()
        }
    }
}