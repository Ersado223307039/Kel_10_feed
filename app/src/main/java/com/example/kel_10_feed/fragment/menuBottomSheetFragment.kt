package com.example.kel_10_feed.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.kel_10_feed.model.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_feed.adapter.MenuAdapter
import com.example.kel_10_feed.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*


class menuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database:FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

        binding.buttonBack.setOnClickListener{
            dismiss()
        }
        retrieveMenuItems()


        return binding.root
    }

    private fun retrieveMenuItems() {
        database= FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")
        menuItems= mutableListOf()

        foodRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               for (foodSnapshot in snapshot.children){
                   val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                   menuItem?.let { menuItems.add(it) }
               }
                Log.d("ITEMS","onDataChange: Data Received")
                setAdapter()
            }



            override fun onCancelled(error: DatabaseError) {

            }

        } )






    }
    private fun setAdapter() {
        if(menuItems.isNotEmpty()){
            val adapter= MenuAdapter(menuItems,requireContext())
            binding.menuRecycleView.layoutManager = LinearLayoutManager(requireContext())
            binding.menuRecycleView.adapter =adapter
            Log.d("item","setAdapter: data set")
        }else{
            Log.d("item","setAdapter: data not set")
        }

    }

    companion object {

    }

}