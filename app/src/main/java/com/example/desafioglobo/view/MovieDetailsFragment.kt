package com.example.desafioglobo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.desafioglobo.MainActivity
import com.example.desafioglobo.R
import com.example.desafioglobo.data.repository.MoviesRepository
import com.example.desafioglobo.databinding.FragmentMovieDetailsBinding
import com.example.desafioglobo.model.Result
import com.example.desafioglobo.viewmodel.HomeViewModel
import com.example.desafioglobo.viewmodel.MovieDetailsViewModel

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailsBinding.bind(view)
        movieDetailsViewModel = ViewModelProvider(this, MovieDetailsViewModel.MovieDetailsViewModelFactory(MoviesRepository())).get(
            MovieDetailsViewModel::class.java)

        (activity as AppCompatActivity).supportActionBar?.hide()

        observerDetailsCallBack()

        val movieId = requireArguments().getString("id")
        movieDetailsViewModel.getMovieDetails(movieId.toString())

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    private fun observerDetailsCallBack() {
        movieDetailsViewModel.movieDetailsLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Result -> setupMovieDetails(response)
            }
        }
    }

    private fun setupMovieDetails(response: Result) {

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/original/${response.poster_path}")
            .into(binding.imgMovie)

        binding.txtMovieTittle.text = response.original_title
        binding.txtTittle.text = response.original_title
        binding.txtSinopsis.text = response.overview
        binding.txtNote.text = response.vote_average.toString()
    }
}