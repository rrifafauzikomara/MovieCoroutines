package com.rifafauzi.moviecoroutines.ui.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rifafauzi.moviecoroutines.R
import com.rifafauzi.moviecoroutines.adapter.MovieAdapter
import com.rifafauzi.moviecoroutines.base.BaseFragment
import com.rifafauzi.moviecoroutines.common.ResultState
import com.rifafauzi.moviecoroutines.databinding.FragmentMovieBinding
import com.rifafauzi.moviecoroutines.model.MovieModel

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : BaseFragment<FragmentMovieBinding, MovieViewModel>(), MovieAdapter.OnMoviesPressedListener {

    private val adapter = MovieAdapter(this)

    override fun getViewModelClass(): Class<MovieViewModel> = MovieViewModel::class.java
    override fun getLayoutResourceId(): Int = R.layout.fragment_movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        vm.movie.observe(viewLifecycleOwner, Observer {
            it?.let { resultState ->
                when (resultState) {
                    is ResultState.Loading -> {
                        hideMovie()
                        showLoading()
                    }
                    is ResultState.HasData -> {
                        showMovie()
                        hideLoading()
                        refreshData(resultState.data)
                    }
                    is ResultState.NoData -> {
                        hideMovie()
                        hideLoading()
                        longSnackBar(resources.getString(R.string.empty_data))
                    }
                    is ResultState.Error -> {
                        hideMovie()
                        hideLoading()
                        longSnackBar(resources.getString(R.string.unknown_error))
                    }
                    is ResultState.NoInternetConnection -> {
                        hideMovie()
                        hideLoading()
                        longSnackBar(resources.getString(R.string.no_internet_connection))
                    }
                    is ResultState.TimeOut -> {
                        hideMovie()
                        hideLoading()
                        longSnackBar(resources.getString(R.string.timeout))
                    }
                }
            }
        })

    }

    override fun onMoviesPressed(movies: MovieModel, position: Int) {
        longSnackBar(movies.title)
    }

    private fun initRecyclerView() {
        binding.rvMovies.adapter = adapter
    }

    private fun refreshData(movies : List<MovieModel>) {
        adapter.submitList(movies)
    }

    private fun showLoading() {
        binding.showLoading = true
    }

    private fun hideLoading() {
        binding.showLoading = false
    }

    private fun showMovie() {
        binding.showData = true
    }

    private fun hideMovie() {
        binding.showData = false
    }


}
