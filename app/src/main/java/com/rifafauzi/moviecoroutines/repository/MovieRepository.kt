package com.rifafauzi.moviecoroutines.repository

import com.rifafauzi.moviecoroutines.model.MovieModel

interface MovieRepository {
    suspend fun getListMovie(): List<MovieModel>
}
