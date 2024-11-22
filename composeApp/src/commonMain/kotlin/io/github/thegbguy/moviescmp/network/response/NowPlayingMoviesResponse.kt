package io.github.thegbguy.moviescmp.network.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class NowPlayingMoviesResponse(

	@SerialName("dates")
	val dates: Dates? = null,

	@SerialName("page")
	val page: Int? = null,

	@SerialName("total_pages")
	val totalPages: Int? = null,

	@SerialName("results")
	val results: List<Movie>? = null,

	@SerialName("total_results")
	val totalResults: Int? = null
)

@Serializable
data class Dates(

	@SerialName("maximum")
	val maximum: String? = null,

	@SerialName("minimum")
	val minimum: String? = null
)
