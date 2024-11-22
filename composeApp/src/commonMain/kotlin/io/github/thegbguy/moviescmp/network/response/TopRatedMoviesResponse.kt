package io.github.thegbguy.moviescmp.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRatedMoviesResponse(

    @SerialName("page")
    val page: Int? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("results")
    val results: List<Movie>? = null,

    @SerialName("total_results")
    val totalResults: Int? = null
)