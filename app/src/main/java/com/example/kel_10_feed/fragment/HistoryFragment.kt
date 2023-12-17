package com.example.kel_10_feed.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kel_10_feed.R
import com.example.kel_10_feed.adapter.BuyAgainAdapter
import com.example.kel_10_feed.databinding.FragmentHistoryBinding
import com.example.kel_10_feed.model.OrderDetails
import com.example.kel_10_feed.recentOrderItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private lateinit var userId:String
    private var listOfOrderItem:MutableList<OrderDetails> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHistoryBinding.inflate(layoutInflater,container,false)

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        retriveBuyHistory()

        binding.recentbuyItem.setOnClickListener {
            seeItemsRecentsBuy()
        }
        binding.ReceivedButton.setOnClickListener {
            updateOrderStatus()
        }
        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPushKey=listOfOrderItem[0].itemPushKey
        val completeOrderReference=database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
    }

    private fun seeItemsRecentsBuy() {
        listOfOrderItem.firstOrNull()?.let{recentBuy->
            val intent=Intent(requireContext(),recentOrderItems::class.java)
            intent.putExtra("RecentBuyOrderItem", ArrayList(listOfOrderItem))
            startActivity(intent)
        }

    }

    private fun retriveBuyHistory() {
        binding.recentbuyItem.visibility=View.INVISIBLE
        userId=auth.currentUser?.uid?:""
        val buyItemReference:DatabaseReference=database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery=buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(buySnapshot in snapshot.children){
                    val buyHistoryItem= buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()){
                    setDataInRecentBuyItem()
                    setPreviousBuyItemRecycleView()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



    }

    private fun setDataInRecentBuyItem() {
        binding.recentbuyItem.visibility=View.VISIBLE
        val recentOrderItem=listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding){
                buyAgainFoodName.text= it.foodNames?.firstOrNull()?:""
                buyAgainFoodPrice.text= it.foodPrices?.firstOrNull()?:""
                val image=it.foodImages?.firstOrNull()
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainFoodImages)

                val isOrderIsAccepted=listOfOrderItem[0].orderAceepted
                if (isOrderIsAccepted){
                    orderStatus.background.setTint(Color.GREEN)
                    ReceivedButton.visibility=View.VISIBLE
                }
            }
        }
    }

    private fun setPreviousBuyItemRecycleView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImages = mutableListOf<String>()
        for (i in 1 until listOfOrderItem.size) {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let {
                buyAgainFoodName.add(it)
                listOfOrderItem[i].foodPrices?.firstOrNull()?.let {
                    buyAgainFoodPrice.add(it)
                    listOfOrderItem[i].foodImages?.firstOrNull()?.let {
                        buyAgainFoodImages.add(it)
                    }

                }


            }
        }
        val rv = binding.BuyAgainRecycleView
        rv.layoutManager = LinearLayoutManager(requireContext())
        buyAgainAdapter = BuyAgainAdapter(
            buyAgainFoodName,
            buyAgainFoodPrice,
            buyAgainFoodImages,
            requireContext()
        )
        rv.adapter = buyAgainAdapter
    }




}