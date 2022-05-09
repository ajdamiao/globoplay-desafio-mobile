package com.example.desafioglobo.data

import com.example.desafioglobo.model.Movie
import com.example.desafioglobo.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBAPI {

    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("api_key") apikey: String,
    ): Response<Movie>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: String,
        @Query("api_key") apikey: String,
    ): Response<Result>

}