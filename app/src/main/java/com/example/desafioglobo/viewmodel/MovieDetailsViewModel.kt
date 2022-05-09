package com.example.desafioglobo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafioglobo.data.repository.MoviesRepository
import com.example.desafioglobo.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(private val repository: MoviesRepository): ViewModel() {
    private var movieDetailsMutableData: MutableLiveData<Result> = MutableLiveData()
    var movieDetailsLiveData: LiveData<Result> = movieDetailsMutableData

    fun getMovieDetails(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val details = withContext(Dispatchers.Default) {
                repository.getMovieDetails(id)
            }

            if (details.isSuccessful) {
                movieDetailsMutableData.value = details.body()
            }
            else {
                println("Exception ${details.message()}")
            }
        }
    }

    class MovieDetailsViewModelFactory(private val repository: MoviesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(repository) as T
        }
    }
}