package com.rifafauzi.moviecoroutines.api

import com.rifafauzi.moviecoroutines.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */

interface ApiService {

    @GET("now_playing")
    suspend fun getMovieNowPlaying(@Query("language") language: String = "en-US") : MovieResponse

}