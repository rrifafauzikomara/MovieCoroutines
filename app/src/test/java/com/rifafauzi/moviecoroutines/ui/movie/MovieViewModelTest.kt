package com.rifafauzi.moviecoroutines.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rifafauzi.moviecoroutines.common.ResultState
import com.rifafauzi.moviecoroutines.model.MovieModel
import com.rifafauzi.moviecoroutines.repository.MovieRepository
import com.rifafauzi.moviecoroutines.utils.InstantTaskExecutorExtension
import com.rifafauzi.moviecoroutines.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class)
class MovieViewModelTest {

    private val _repository= mockk<MovieRepository>()
    private lateinit var _movieViewModel:MovieViewModel
    private val _movieModel = mockk<MovieModel>()
    val _dispatcher = Dispatchers.Unconfined

    @get:Rule
    var mainCoroutineRule =
        MainCoroutineRule()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Test
    fun getMovie() {


        mainCoroutineRule.runBlockingTest {
            val movieResponse =
                listOf<MovieModel>()


            coEvery { _repository.getListMovie() } returns movieResponse
            _movieViewModel = MovieViewModel(_repository,
                _dispatcher,
                _dispatcher
            )
            _movieViewModel.movie.observeForever {  }
            _movieViewModel.getNowPlaying()

            val isLoading = _movieViewModel.movie.value.takeIf {
                it is ResultState.Loading
            }
            assert(_movieViewModel.movie.value == isLoading)

            val isMovieModel =
                _movieViewModel.movie.value.takeIf {
                    it is ResultState.HasData
                }
            assert(_movieViewModel.movie.value == isMovieModel)
        }
    }
}
