package com.example.desafioglobo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafioglobo.R
import com.example.desafioglobo.adapter.MovieAdapter
import com.example.desafioglobo.data.repository.MoviesRepository
import com.example.desafioglobo.databinding.FragmentHomeBinding
import com.example.desafioglobo.model.Movie
import com.example.desafioglobo.viewmodel.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        homeViewModel = ViewModelProvider(this, HomeViewModel.HomeViewModelFactory(MoviesRepository())).get(HomeViewModel::class.java)

        observerMoviesCallBack()
        homeViewModel.callGetMovieList()
    }

    private fun observerMoviesCallBack() {

        homeViewModel.movieLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Movie -> setupMovieRecyclerView(response)
            }
        }

    }

    private fun setupMovieRecyclerView(movie: Movie) {

        binding.movieRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.movieRecyclerView.adapter = MovieAdapter(movie, requireContext())


    }
}