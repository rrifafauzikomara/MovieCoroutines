package com.rifafauzi.moviecoroutines.model

import com.google.gson.annotations.SerializedName

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */

data class MovieModel(
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("backdrop_path")
    val backdrop_path: String?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>?,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val vote_average: Double?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val release_date: String?
)