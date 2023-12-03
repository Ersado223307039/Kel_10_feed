package com.example.kel_10_feed.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_feed.R
import com.example.kel_10_feed.adapter.BuyAgainAdapter
import com.example.kel_10_feed.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHistoryBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun setupRecycleView(){
        val buyAgainFoodName= arrayListOf("food 2","food 2","food 3")
        val buyAgainFoodPrice= arrayListOf("$2","$3","$5")
        val buyAgainFoodImages= arrayListOf(R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3)
        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImages)
        binding.BuyAgainRecycleView.adapter=buyAgainAdapter
        binding.BuyAgainRecycleView.layoutManager=LinearLayoutManager(requireContext())
    }

    companion object {


    }
}