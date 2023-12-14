package com.example.kel_10_feed

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kel_10_feed.databinding.ActivityDetailBinding
import com.example.kel_10_feed.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    private var foodName:String?=null
    private var foodimage:String?=null
    private var foodDescription:String?=null
    private var foodIngredients:String?=null
    private var foodPrice:String?=null
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        auth= Firebase.auth

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
        binding.addItembutton.setOnClickListener {
            addItemCart()

        }
    }

    private fun addItemCart() {
        val database=FirebaseDatabase.getInstance().reference

        val userId=auth.currentUser?.uid?:""

        val cartItem=CartItems(foodName.toString(),foodPrice.toString(),foodDescription.toString(),foodimage.toString(),1)
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "items add succesfull", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "item not added", Toast.LENGTH_SHORT).show()
        }
    }
}