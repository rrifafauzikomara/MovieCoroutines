package com.rifafauzi.moviecoroutines.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifafauzi.moviecoroutines.R
import com.rifafauzi.moviecoroutines.api.ApiService
import com.rifafauzi.moviecoroutines.common.ResultState
import com.rifafauzi.moviecoroutines.model.MovieModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */
 
class MovieViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _movie = MutableLiveData<ResultState<List<MovieModel>>>()
    val movie: LiveData<ResultState<List<MovieModel>>> get() = _movie

    private fun setResultNowPlaying(resultState: ResultState<List<MovieModel>>) {
        _movie.postValue(resultState)
    }

    init {
        getNowPlaying()
    }

    private fun getNowPlaying() {
        setResultNowPlaying(ResultState.Loading())
        viewModelScope.launch {
            val response = apiService.getMovieNowPlaying()
            val result = response.results
            try {
                if (result.isEmpty()) {
                    setResultNowPlaying(ResultState.NoData())
                    return@launch
                }
                setResultNowPlaying(ResultState.HasData(result))
            } catch (e: Throwable) {
                when (e) {
                    is IOException -> setResultNowPlaying(ResultState.NoInternetConnection(R.string.no_internet_connection))
                    is TimeoutException ->  setResultNowPlaying(ResultState.TimeOut(R.string.timeout))
                    else -> setResultNowPlaying(ResultState.Error(R.string.unknown_error))
                }
            }
        }
    }

}