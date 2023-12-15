package com.example.kel_10_feed.fragment

import androidx.appcompat.widget.SearchView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_feed.adapter.MenuAdapter
import com.example.kel_10_feed.databinding.FragmentSearchBinding
import com.example.kel_10_feed.model.MenuItem
import com.google.firebase.database.*


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter  : MenuAdapter
    private lateinit var database:FirebaseDatabase
    private val originalMenuItems= mutableListOf<MenuItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        retriveMenuItem()

        setupSearchView()



        return binding.root
    }

    private fun retriveMenuItem() {
        database=FirebaseDatabase.getInstance()
        val foodReference:DatabaseReference=database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        originalMenuItems.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showAllMenu() {

        val filteredMenuItem=ArrayList(originalMenuItems)
        setAdapter(filteredMenuItem)



    }

    private fun setAdapter(filteredMenuItem: List<MenuItem>) {
        adapter= MenuAdapter(filteredMenuItem,requireContext())
        binding.menuRecycleView.layoutManager=LinearLayoutManager(requireContext())
        binding.menuRecycleView.adapter=adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })

    }

    private fun filterMenuItems(query: String) {
        val filteredMenuItems=originalMenuItems.filter {
            it.foodName?.contains(query,ignoreCase = true) == true
        }
        setAdapter(filteredMenuItems )
    }
    companion object {

    }

}