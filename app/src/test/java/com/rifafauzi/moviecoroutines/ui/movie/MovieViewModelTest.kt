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
import java.io.IOException
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class)
class MovieViewModelTest {

    private val _repository= mockk<MovieRepository>()
    private lateinit var _movieViewModel:MovieViewModel
//    private val _movieModel = mockk<MovieModel>()
    private val _dispatcher = Dispatchers.Unconfined

    @get:Rule
    var mainCoroutineRule =
        MainCoroutineRule()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test loading before get data movie should return result loading`(){
        mainCoroutineRule.runBlockingTest {
            coEvery { _repository.getListMovie() }

            _movieViewModel = MovieViewModel(
                _repository,
                _dispatcher,
                _dispatcher
            )

            _movieViewModel.getNowPlaying()
            _movieViewModel.movie.observeForever {  }

            val isLoading = _movieViewModel.movie.value.takeIf {
                it is ResultState.Error
            }
            assert(_movieViewModel.movie.value == isLoading)
        }
    }

    @Test
    fun `test result after get data movie should return result has data`() {
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

            val isMovieModel =
                _movieViewModel.movie.value.takeIf {
                    it is ResultState.HasData
                }
            assert(_movieViewModel.movie.value == isMovieModel)

        }
    }

    @Test
    fun `test result after get no data movie should return result no data`() {
        mainCoroutineRule.runBlockingTest {

            coEvery { _repository.getListMovie() } returns emptyList()
            _movieViewModel = MovieViewModel(_repository,
                _dispatcher,
                _dispatcher
            )

            _movieViewModel.movie.observeForever {  }
            _movieViewModel.getNowPlaying()

            val isNoData =
                _movieViewModel.movie.value.takeIf {
                    it is ResultState.NoData
                }
            assert(_movieViewModel.movie.value == isNoData)
        }
    }

    @Test
    fun `test catch when throw exception should return result error`(){
        mainCoroutineRule.runBlockingTest {
            val throwable =
                Throwable()
            coEvery { _repository.getListMovie() } throws throwable

            _movieViewModel = MovieViewModel(_repository,
                _dispatcher,
                _dispatcher
            )

            _movieViewModel.getNowPlaying()
            _movieViewModel.movie.observeForever {  }

            val isLoading = _movieViewModel.movie.value.takeIf {
                it is ResultState.Error
            }
            assert(_movieViewModel.movie.value == isLoading)
        }
    }

    @Test
    fun `test catch when throw IOException should return result NoInternetConnection`(){
        mainCoroutineRule.runBlockingTest {
            val ioException =
                IOException()
            coEvery { _repository.getListMovie() } throws ioException

            _movieViewModel = MovieViewModel(_repository,
                _dispatcher,
                _dispatcher
            )

            _movieViewModel.getNowPlaying()
            _movieViewModel.movie.observeForever {  }

            val isNoInternetConnection =
                _movieViewModel.movie.value.takeIf {
                it is ResultState.NoInternetConnection
            }
            assert(_movieViewModel.movie.value == isNoInternetConnection)
        }
    }

    @Test
    fun `test catch when throw TimeoutException should return result TimeOut`(){
        mainCoroutineRule.runBlockingTest {
            val timeOutException =
                TimeoutException()
            coEvery { _repository.getListMovie() } throws timeOutException

            _movieViewModel = MovieViewModel(_repository,
                _dispatcher,
                _dispatcher
            )

            _movieViewModel.getNowPlaying()
            _movieViewModel.movie.observeForever {  }

            val isTimeout =
                _movieViewModel.movie.value.takeIf {
                it is ResultState.NoInternetConnection
            }
            assert(_movieViewModel.movie.value == isTimeout)
        }
    }

}
