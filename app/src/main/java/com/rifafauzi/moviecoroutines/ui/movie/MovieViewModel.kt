package com.rifafauzi.moviecoroutines.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifafauzi.moviecoroutines.R
import com.rifafauzi.moviecoroutines.common.ResultState
import com.rifafauzi.moviecoroutines.model.MovieModel
import com.rifafauzi.moviecoroutines.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by rrifafauzikomara on 2020-02-20.
 */

class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    @Named("IO") private val backgroundDispatcher: CoroutineDispatcher = IO,
    @Named("MAIN") private val mainCoroutineDispatcher: CoroutineDispatcher = Main
) : ViewModel() {

    private val _movie = MutableLiveData<ResultState<List<MovieModel>>>()
    val movie: LiveData<ResultState<List<MovieModel>>> get() = _movie

    private fun setResultNowPlaying(resultState: ResultState<List<MovieModel>>) {
        _movie.postValue(resultState)
    }

    init {
        getNowPlaying()
    }

    internal fun getNowPlaying() {
        viewModelScope.launch(mainCoroutineDispatcher) {
            try {
                setResultNowPlaying(ResultState.Loading())
                val result = async(context = backgroundDispatcher) { repository.getListMovie() }
                showNoData(result)
                showHasData(result)
            } catch (e: Throwable) {
                when (e) {
                    is IOException -> setResultNowPlaying(ResultState.NoInternetConnection(R.string.no_internet_connection))
                    is TimeoutException -> setResultNowPlaying(ResultState.TimeOut(R.string.timeout))
                    else -> setResultNowPlaying(ResultState.Error(R.string.unknown_error))
                }
            }
        }
    }

    internal suspend fun showHasData(result: Deferred<List<MovieModel>>) {
        setResultNowPlaying(ResultState.HasData(result.await())).takeUnless {
            result.await().isEmpty()
        }
    }

    internal suspend fun showNoData(result: Deferred<List<MovieModel>>) {
        setResultNowPlaying(ResultState.NoData()).takeIf {
            result.await().isEmpty()
        }
    }
}
