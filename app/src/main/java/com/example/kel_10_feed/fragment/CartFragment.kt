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
            val intent= Intent(requireContext(),PayOutActivity::class.java)
            startActivity(intent)
        }

        return binding.root

    }

    private fun getOrderItemDetail() {

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
                val adapter=CartAdapter(requireContext(),foodNames,foodPrices,foodDescriptions,foodImageUri,quantity,foodIngredients)
                binding.cartRecycleView.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.cartRecycleView.adapter =adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data not fetch", Toast.LENGTH_SHORT).show()

            }

        })
    }

    companion object {

    }
}