package com.rifafauzi.moviecoroutines.repository

import com.rifafauzi.moviecoroutines.api.ApiService
import com.rifafauzi.moviecoroutines.model.MovieModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(private val apiService: ApiService):MovieRepository {
    override suspend fun getListMovie(): List<MovieModel> =
        apiService.getMovieNowPlaying().results.asSequence()
            .toList()
}