package com.example.desafioglobo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.desafioglobo.R
import com.example.desafioglobo.adapter.FavoriteMovieListAdapter
import com.example.desafioglobo.adapter.MovieAdapter
import com.example.desafioglobo.data.DatabaseHelper
import com.example.desafioglobo.databinding.FragmentMyListBinding
import com.example.desafioglobo.model.Movie
import com.example.desafioglobo.model.Result

class MyListFragment : Fragment(R.layout.fragment_my_list) {
    private lateinit var binding: FragmentMyListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyListBinding.bind(view)

        val dbHelper = DatabaseHelper(requireContext())

        val allResults = dbHelper.getAllResults()
        setupMovieRecyclerView(allResults)
        println(allResults)
    }

    private fun setupMovieRecyclerView(movieList: List<Result>) {
        binding.movieRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.movieRecyclerView.adapter = FavoriteMovieListAdapter(movieList, requireContext())
    }
}