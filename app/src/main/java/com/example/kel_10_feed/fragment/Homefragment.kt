package com.example.kel_10_feed.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kel_10_feed.R
import com.example.kel_10_feed.adapter.MenuAdapter
import com.example.kel_10_feed.adapter.PopulerAdapter
import com.example.kel_10_feed.databinding.FragmentHomefragmentBinding
import com.example.kel_10_feed.model.MenuItem
import com.google.firebase.database.*


class Homefragment : Fragment() {
    private lateinit var  binding: FragmentHomefragmentBinding
    private lateinit var database:FirebaseDatabase
    private lateinit var menuitems:MutableList<MenuItem>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomefragmentBinding.inflate(inflater,container,false)
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = menuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"tes")
        }
        retraieveAndDisplayPopulerMeu()
        return binding.root




    }

    private fun retraieveAndDisplayPopulerMeu() {
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")
        menuitems= mutableListOf()

        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuitems.add(it) }
                }
                randomPopulerItem()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun randomPopulerItem() {
       val index = menuitems.indices.toList().shuffled()
        val numItemToShow=6
        val subsetMenuItems=index.take(numItemToShow).map { menuitems[it] }
        
        setPopulerItemAdapter(subsetMenuItems)
    }

    private fun setPopulerItemAdapter(subsetMenuItems: List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenuItems,requireContext())
        binding.populerRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.populerRecycleView.adapter=adapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)

        imageSlider.setItemClickListener(object :ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }


            override fun onItemSelected(position: Int) {
               val itemPosition = imageList[position]
                val itemMessage="Selected Image $position"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_SHORT).show()
            }


        })



    }

}