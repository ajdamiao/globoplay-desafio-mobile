package com.example.desafioglobo.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafioglobo.R
import com.example.desafioglobo.databinding.RviewMoviesBinding
import com.example.desafioglobo.model.Movie

class MovieAdapter(private val movies: Movie, val context: Context):
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: RviewMoviesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MovieAdapter.MovieViewHolder {
        val binding = RviewMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {

        with(holder) {
            with(movies.results[position]) {

                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original/${movies.results[position].poster_path}")
                    .into(binding.movieImage)

                holder.itemView.setOnClickListener {
                    val id = movies.results[position].id
                    val bundle = Bundle()

                    bundle.putString("id", id.toString())
                    Navigation.findNavController(itemView).navigate(R.id.movieDetailsFragment, bundle)
                }
            }
        }
    }

    override fun getItemCount() = movies.results.size
}