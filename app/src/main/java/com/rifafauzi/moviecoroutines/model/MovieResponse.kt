package com.rifafauzi.moviecoroutines.model

import com.google.gson.annotations.SerializedName

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */
 
data class MovieResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_results")
    val total_results: Int?,
    @SerializedName("total_pages")
    val total_pages: Int?,
    @SerializedName("results")
    val results: List<MovieModel>
)