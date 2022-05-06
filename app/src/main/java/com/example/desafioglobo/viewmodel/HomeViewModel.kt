package com.example.desafioglobo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafioglobo.data.repository.MoviesRepository
import com.example.desafioglobo.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: MoviesRepository): ViewModel() {
    private val movieMutableData: MutableLiveData<Movie> = MutableLiveData()
    val movieLiveData = movieMutableData


    fun callGetMovieList() {
        CoroutineScope(Dispatchers.Main).launch {
            val movies = withContext(Dispatchers.Default) {
                repository.getMovieList()
            }

            if(movies.isSuccessful) {
                movieMutableData.value = movies.body()
            }
            else {
                println("Exception ${movies.message()}")
            }

        }

    }

    class HomeViewModelFactory(private val repository: MoviesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }

}