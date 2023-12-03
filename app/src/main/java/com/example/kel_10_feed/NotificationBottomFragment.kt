package com.example.kel_10_feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_feed.adapter.NotificationAdapter
import com.example.kel_10_feed.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottomFragment : BottomSheetDialogFragment(){
    private lateinit var binding: FragmentNotificationBottomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNotificationBottomBinding.inflate(layoutInflater,container,false)
        val notification = listOf("Your order Has Been Canceled Succecfully","order has been taken by the driver","congrats your order placed")
        val notificationImages = listOf(R.drawable.sademoji,
        R.drawable.truck,R.drawable.congratulation)
        val adapter= NotificationAdapter(
           ArrayList(notification),
            ArrayList(notificationImages)
        )
        binding.notificationRecycleView.layoutManager=LinearLayoutManager(requireContext())
        binding.notificationRecycleView.adapter=adapter
        return binding.root
    }

    companion object {

    }
}