package com.example.kel_10_feed.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kel_10_feed.DetailActivity
import com.example.kel_10_feed.databinding.PopulerItemBinding

class PopulerAdapter (private val items:List<String>,private val price:List<String>,private val image:List<Int>, private val requaireContext: Context):RecyclerView.Adapter<PopulerAdapter.PopulerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopulerViewHolder {
        return PopulerViewHolder(PopulerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: PopulerViewHolder, position: Int) {
        val item = items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item,price,images)

        holder.itemView.setOnClickListener{
            val intent = Intent(requaireContext, DetailActivity::class.java)
            intent.putExtra("MenuItemName",item)
            intent.putExtra("MenuItemImage",images)
            requaireContext.startActivity(intent)
        }
        
    }
    override fun getItemCount(): Int {
        return items.size
    }

    class PopulerViewHolder(private val binding: PopulerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imagesView = binding.imageView4
        fun bind(item: String, price: String, images: Int) {
            binding.foodNamePopuler.text = item
            binding.pricePopuler.text =price
            imagesView.setImageResource(images)


        }


    }

}