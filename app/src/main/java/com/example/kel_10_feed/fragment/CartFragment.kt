package com.example.kel_10_feed.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_feed.PayOutActivity
import com.example.kel_10_feed.adapter.CartAdapter
import com.example.kel_10_feed.databinding.FragmentCartBinding
import com.example.kel_10_feed.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var foodNames:MutableList<String>
    private lateinit var foodPrices:MutableList<String>
    private lateinit var foodDescriptions:MutableList<String>
    private lateinit var foodImageUri:MutableList<String>
    private lateinit var foodIngredients:MutableList<String>
    private lateinit var quantity:MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCartBinding.inflate(inflater,container,false)
        auth=FirebaseAuth.getInstance()
        retriveCartItems()


        binding.procesedBotton.setOnClickListener{
            getOrderItemDetail()

        }

        return binding.root

    }

    private fun getOrderItemDetail() {
        val orderIdReference:DatabaseReference=database.reference.child("user").child(userId).child("CartItems")
        val foodName= mutableListOf<String>()
        val foodPrice= mutableListOf<String>()
        val foodImage= mutableListOf<String>()
        val foodDescription= mutableListOf<String>()
        val foodIngredients= mutableListOf<String>()
        val foodQuantities= cartAdapter.getupdatedItemQuantities()
        orderIdReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val orderItems=foodSnapshot.getValue(CartItems::class.java)
                    orderItems?.foodname?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodIngredint?.let { foodIngredients.add(it) }
                }
                orderNow(foodName,foodPrice,foodDescription,foodImage,foodIngredients,foodQuantities)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "order making failed. Please try again", Toast.LENGTH_SHORT).show()

            }

        })


    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodIngredients: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
       if (isAdded && context!=null){
           val intent=Intent(requireContext(),PayOutActivity::class.java)
           intent.putExtra("FoodItemName",foodName as ArrayList<String>)
           intent.putExtra("FoodItemPrice",foodPrice as ArrayList<String>)
           intent.putExtra("FoodItemImage",foodImage as ArrayList<String>)
           intent.putExtra("FoodItemDescription",foodDescription as ArrayList<String>)
           intent.putExtra("FoodItemIngredient",foodIngredients as ArrayList<String>)
           intent.putExtra("FoodItemQuantities",foodQuantities as ArrayList<Int>)
           startActivity(intent)
       }


    }

    private fun retriveCartItems() {
        database=FirebaseDatabase.getInstance()
        userId=auth.currentUser?.uid?:""
        val foodReference:DatabaseReference=database.reference.child("user").child(userId).child("CartItems")


        foodNames= mutableListOf()
        foodPrices= mutableListOf()
        foodDescriptions= mutableListOf()
        foodImageUri= mutableListOf()
        foodIngredients= mutableListOf()
        quantity= mutableListOf()

        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val cartItems =foodSnapshot.getValue(CartItems::class.java)

                    cartItems?.foodname?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodImage?.let { foodImageUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                    cartItems?.foodIngredint?.let { foodIngredients.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter=CartAdapter(
                    requireContext(),
                    foodNames,
                    foodPrices,
                    foodDescriptions,
                    foodImageUri,
                    quantity,
                    foodIngredients)
                binding.cartRecycleView.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.cartRecycleView.adapter =cartAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data not fetch", Toast.LENGTH_SHORT).show()

            }

        })
    }

    companion object {

    }
}