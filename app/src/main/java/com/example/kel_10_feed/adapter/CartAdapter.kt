package com.example.kel_10_feed.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.kel_10_feed.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*



class CartAdapter(
    private val context:Context,
    private val CartItems:MutableList<String>,
    private  val cartItemPrices:MutableList<String>,
    private var cartDescriptions:MutableList<String>,
    private var cartImages:MutableList<String>,
    private val cartQuantity:MutableList<Int>,
    private var cartIngredient:MutableList<String>


    ):RecyclerView.Adapter<CartAdapter.CartViewHolder> (){
    private val auth=FirebaseAuth.getInstance()
    init {
        val database=FirebaseDatabase.getInstance()
        val userId=auth.currentUser?.uid?:""
        val cartItmeNumber=CartItems.size

        ItemQuanlities= IntArray(cartItmeNumber){1}
        cartItemReference= database.reference.child("user").child(userId).child("CartItems")


    }
    companion object{
        private var ItemQuanlities:IntArray= intArrayOf()
        private lateinit var cartItemReference:DatabaseReference
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =CartItems.size
    fun getupdatedItemQuantities():MutableList<Int> {
        val itemQuantity= mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity

    }

    inner class CartViewHolder(private val binding: CartItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quality= ItemQuanlities[position]
                cartFoodName.text=CartItems[position]
                cartItemPrice.text=cartItemPrices[position]

                cartquantity.text=quality.toString()

                val uriString=cartImages[position]
                Log.d("image", "food Url: $uriString ")
                val uri=Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartImage)
                minusButton.setOnClickListener{
                    deceaseQuantity(position)
                }


                plusButton.setOnClickListener {
                    increaseQuantity(position)


                }
                deleteButton.setOnClickListener {
                    val itemPosition = bindingAdapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION){
                        deleteItem(itemPosition)
                    }

                }
            }

        }
        private  fun deceaseQuantity(position: Int){
            if ( ItemQuanlities[position]>1){
                ItemQuanlities[position]--
                binding.cartquantity.text=  ItemQuanlities[position].toString()
            }

        }

        private fun increaseQuantity(position: Int){
            if (ItemQuanlities[position]<10){
                ItemQuanlities[position]++
                binding.cartquantity.text=  ItemQuanlities[position].toString()

            }

        }

        private fun deleteItem(position: Int) {
         val positionRetrive= bindingAdapterPosition
            getUniqueKeyPosition(positionRetrive){uniqueKey ->
                if (uniqueKey !=null){
                    removeItem(position,uniqueKey)
                }
            }


        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if (uniqueKey !=null)
                cartItemReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    CartItems.removeAt(position)
                    cartImages.removeAt(position)
                    cartDescriptions.removeAt(position)
                    cartQuantity.removeAt(position)
                    cartItemPrices.removeAt(position)
                    cartIngredient.removeAt(position)
                    Toast.makeText(context, "item deleted", Toast.LENGTH_SHORT).show()

                    ItemQuanlities= ItemQuanlities.filterIndexed { index, i -> index!=position  }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,CartItems.size)

                }.addOnFailureListener{
                    Toast.makeText(context, "failrd to delete", Toast.LENGTH_SHORT).show()

                }

        }

        private fun getUniqueKeyPosition(positionRetrive: Int,onComplete:(String?)->Unit) {
            cartItemReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey:String?=null
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index==positionRetrive){
                            uniqueKey=dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }
    }
}



