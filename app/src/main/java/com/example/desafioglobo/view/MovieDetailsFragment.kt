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
import com.example.desafioglobo.data.DatabaseHelper
import com.example.desafioglobo.data.repository.MoviesRepository
import com.example.desafioglobo.databinding.FragmentMovieDetailsBinding
import com.example.desafioglobo.model.Result
import com.example.desafioglobo.viewmodel.HomeViewModel
import com.example.desafioglobo.viewmodel.MovieDetailsViewModel

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var movie: Result

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailsBinding.bind(view)
        movieDetailsViewModel = ViewModelProvider(this, MovieDetailsViewModel.MovieDetailsViewModelFactory(MoviesRepository())).get(
            MovieDetailsViewModel::class.java)

        (activity as AppCompatActivity).supportActionBar?.hide()

        val dbHelper = DatabaseHelper(requireContext())
        observerDetailsCallBack()

        val movieId = requireArguments().getString("id")
        movieDetailsViewModel.getMovieDetails(movieId.toString())

        val allResults: List<Result> = dbHelper.getAllResults()

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        binding.btnMyList.setOnClickListener {
            if(movie != null) {
                val result = Result(
                    adult = movie.adult ?: false,
                    backdrop_path = movie.backdrop_path,
                    genre_ids = movie.genre_ids ?: emptyList(),
                    id = movie.id ?: 0,
                    original_language = movie.original_language ?: "",
                    original_title = movie.original_title ?: "",
                    overview = movie.overview ?: "",
                    popularity = movie.popularity ?: 0.0,
                    poster_path = movie.poster_path ?: "",
                    release_date = movie.release_date ?: "",
                    title = movie.title ?: "",
                    video = movie.video ?: false,
                    vote_average = movie.vote_average ?: 0.0,
                    vote_count = movie.vote_count ?: 0
                )
                dbHelper.insertResult(result)
            }

            val allResults = dbHelper.getAllResults()
            allResults.forEach { println(it) }
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
        movie = response
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/original/${response.poster_path}")
            .into(binding.imgMovie)

        binding.txtMovieTittle.text = response.original_title
        binding.txtTittle.text = response.original_title
        binding.txtSinopsis.text = response.overview
        binding.txtNote.text = response.vote_average.toString()
    }
}