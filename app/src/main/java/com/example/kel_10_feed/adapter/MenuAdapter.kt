package com.example.kel_10_feed.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kel_10_feed.model.MenuItem
import com.example.kel_10_feed.DetailActivity
import com.example.kel_10_feed.databinding.MenuItemBinding



class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requaireContext:Context
    ) :RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
       val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }




    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder (private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                   openDetailActivity(position)
                }


            }
        }

        private fun openDetailActivity(position: Int) {
            val menuItem=menuItems[position]
            val intent = Intent(requaireContext,DetailActivity::class.java).apply {
                putExtra("menuItemName",menuItem.foodName)
                putExtra("menuItemImage",menuItem.foodImage)
                putExtra("menuItemDescription",menuItem.foodDescription)
                putExtra("menuItemIngredient",menuItem.foodIngredient)
                putExtra("menuItemPrice",menuItem.foodPrice)
            }
            requaireContext.startActivity(intent)

        }

        fun bind(position: Int) {
            val menuItem=menuItems[position]
            binding.apply {
                menuFoodName.text= menuItem.foodName
                menuPrice.text=menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requaireContext).load(uri).into(menuImage)



            }

        }

    }


}


