package com.example.kel_10_feed.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kel_10_feed.DetailActivity
import com.example.kel_10_feed.databinding.MenuItemBinding

@Suppress("DEPRECATION")
class MenuAdapter(private val menuItemsName:MutableList<String>, private val menuItemPrice: MutableList<String>, private val MenuImage:MutableList<Int>, private val requaireContext:Context) :RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){
private val itemClickListener : OnClickListener ?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
       val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }




    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItemsName.size

    inner class MenuViewHolder (private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    itemClickListener?.onItemClick(position)
                }
                val intent = Intent(requaireContext,DetailActivity::class.java)
                intent.putExtra("MenuItemName",menuItemsName.get(position))
                intent.putExtra("MenuItemImage",MenuImage.get(position))
                requaireContext.startActivity(intent)

            }
        }
        fun bind(position: Int) {
            binding.apply {
                menuFoodName.text= menuItemsName[position]
                menuPrice.text=menuItemPrice[position]
                menuImage.setImageResource(MenuImage[position])






            }

        }

    }
    interface OnClickListener{
        fun onItemClick(position: Int)

    }

}


