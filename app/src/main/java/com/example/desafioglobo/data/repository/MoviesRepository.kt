package com.example.desafioglobo.data.repository

import com.example.desafioglobo.data.TheMovieDBAPI
import com.example.desafioglobo.model.Movie
import com.example.desafioglobo.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRepository {
    private lateinit var baseUrl: String

    private fun makeRequest():TheMovieDBAPI {
        baseUrl = "https://api.themoviedb.org/3/"

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBAPI::class.java)
    }

    suspend fun getMovieList(): Response<Movie> {
        return withContext(Dispatchers.IO) {
            makeRequest().getMovieList("c6dc59b1a1abb68df92660526778579d")
        }
    }

    suspend fun getMovieDetails(id: String): Response<Result> {
        return withContext(Dispatchers.IO) {
            makeRequest().getMovieDetails(id,"c6dc59b1a1abb68df92660526778579d")
        }
    }

}