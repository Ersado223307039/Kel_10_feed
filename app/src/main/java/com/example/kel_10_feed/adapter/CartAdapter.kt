package com.example.kel_10_feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kel_10_feed.databinding.CartItemBinding

class CartAdapter(private val cartItems:MutableList<String>,private  val CartItemsPrice:MutableList<String>,private var cartImages:MutableList<Int>):RecyclerView.Adapter<CartAdapter.CartViewHolder> (){
    private val itemQualities = IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =cartItems.size

    inner class CartViewHolder(private val binding: CartItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quality= itemQualities[position]
                cartFoodName.text=cartItems[position]
                cartItemPrice.text=CartItemsPrice[position]
                cartImage.setImageResource(cartImages[position])
                cartquantity.text=quality.toString()

                minusButton.setOnClickListener{
                    deceaseQuantity(position)
                }


                plusButton.setOnClickListener {
                    increaseQuantity(position)


                }
                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION){
                        deleteItem(itemPosition)
                    }

                }
            }

        }
        private    fun deceaseQuantity(position: Int){
            if (itemQualities[position]>1){
                itemQualities[position]--
                binding.cartquantity.text= itemQualities[position].toString()
            }

        }

        private fun increaseQuantity(position: Int){
            if (itemQualities[position]<10){
                itemQualities[position]++
                binding.cartquantity.text= itemQualities[position].toString()

            }

        }

        private fun deleteItem(position: Int) {
            cartItems.removeAt(position)
            cartImages.removeAt(position)
            CartItemsPrice.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeRemoved(position, cartItems.size)


        }
    }
}



