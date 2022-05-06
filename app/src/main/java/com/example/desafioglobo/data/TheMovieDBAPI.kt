package com.example.desafioglobo.data

import com.example.desafioglobo.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBAPI {

    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("api_key") apikey: String,
    ): Response<Movie>

}