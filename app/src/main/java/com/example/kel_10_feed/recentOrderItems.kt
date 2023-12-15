package com.example.kel_10_feed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_feed.adapter.RecentBuyAdapter
import com.example.kel_10_feed.databinding.ActivityRecentOrderItemsBinding
import com.example.kel_10_feed.model.OrderDetails

class recentOrderItems : AppCompatActivity() {
    private  val binding:ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames:ArrayList<String>
    private lateinit var allFoodImages:ArrayList<String>
    private lateinit var allFoodPrices:ArrayList<String>
    private lateinit var allFoodQuantities:ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backbutton.setOnClickListener {
            finish()
        }


        val recentOrderItems = intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderItems?.let{orderDetails ->
            if (orderDetails.isNotEmpty()){
                val recentOrderItems=orderDetails[0]
                allFoodNames=recentOrderItems.foodNames as ArrayList<String>
                allFoodImages=recentOrderItems.foodImages as ArrayList<String>
                allFoodPrices=recentOrderItems.foodPrices as ArrayList<String>
                allFoodQuantities=recentOrderItems.foodQuantities as ArrayList<Int>





            }

        }
        setAdapter()

    }

    private fun setAdapter() {
        val rv = binding.recyclerViewRecentBuy
        rv.layoutManager=LinearLayoutManager(this)
        val adapter=RecentBuyAdapter(this,allFoodNames,allFoodImages,allFoodPrices,allFoodQuantities)
        rv.adapter=adapter

    }
}