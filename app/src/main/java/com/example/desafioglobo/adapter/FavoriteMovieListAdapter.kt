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
import com.example.desafioglobo.model.Result

class FavoriteMovieListAdapter(private val movies: List<Result>, val context: Context):
    RecyclerView.Adapter<FavoriteMovieListAdapter.FavoriteMovieListHolder>() {

    inner class FavoriteMovieListHolder(val binding: RviewMoviesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieListHolder {
        val binding = RviewMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteMovieListHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieListHolder, position: Int) {
        with(holder) {
            with(movies[position]) {

                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original/${movies[position].poster_path}")
                    .into(binding.movieImage)

                holder.itemView.setOnClickListener {
                    val id = movies[position].id
                    val bundle = Bundle()

                    bundle.putString("id", id.toString())
                    Navigation.findNavController(itemView).navigate(R.id.movieDetailsFragment, bundle)
                }
            }
        }
    }

    override fun getItemCount() = movies.size
}