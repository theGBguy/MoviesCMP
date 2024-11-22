package io.github.thegbguy.moviescmp.network.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieDetailsResponse(

	@SerialName("original_language")
	val originalLanguage: String? = null,

	@SerialName("imdb_id")
	val imdbId: String? = null,

	@SerialName("video")
	val video: Boolean? = null,

	@SerialName("title")
	val title: String? = null,

	@SerialName("backdrop_path")
	val backdropPath: String? = null,

	@SerialName("revenue")
	val revenue: Int? = null,

	@SerialName("genres")
	val genres: List<Genre>? = null,

	@SerialName("popularity")
	val popularity: Double? = null,

	@SerialName("production_countries")
	val productionCountries: List<ProductionCountry>? = null,

	@SerialName("id")
	val id: Int,

	@SerialName("vote_count")
	val voteCount: Int? = null,

	@SerialName("budget")
	val budget: Int? = null,

	@SerialName("overview")
	val overview: String? = null,

	@SerialName("original_title")
	val originalTitle: String? = null,

	@SerialName("runtime")
	val runtime: Int? = null,

	@SerialName("poster_path")
	val posterPath: String? = null,

	@SerialName("origin_country")
	val originCountry: List<String>? = null,

	@SerialName("spoken_languages")
	val spokenLanguages: List<SpokenLanguage>? = null,

	@SerialName("production_companies")
	val productionCompanies: List<ProductionCompany>? = null,

	@SerialName("release_date")
	val releaseDate: String? = null,

	@SerialName("vote_average")
	val voteAverage: Double? = null,

	@SerialName("belongs_to_collection")
	val belongsToCollection: BelongsToCollection? = null,

	@SerialName("tagline")
	val tagline: String? = null,

	@SerialName("adult")
	val adult: Boolean? = null,

	@SerialName("homepage")
	val homepage: String? = null,

	@SerialName("status")
	val status: String? = null
)

@Serializable
data class SpokenLanguage(

	@SerialName("name")
	val name: String? = null,

	@SerialName("iso_639_1")
	val iso6391: String? = null,

	@SerialName("english_name")
	val englishName: String? = null
)

@Serializable
data class BelongsToCollection(

	@SerialName("backdrop_path")
	val backdropPath: String? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("poster_path")
	val posterPath: String? = null
)

@Serializable
data class ProductionCountry(

	@SerialName("iso_3166_1")
	val iso31661: String? = null,

	@SerialName("name")
	val name: String? = null
)

@Serializable
data class Genre(

	@SerialName("name")
	val name: String? = null,

	@SerialName("id")
	val id: Int? = null
)

@Serializable
data class ProductionCompany(

	@SerialName("logo_path")
	val logoPath: String? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("origin_country")
	val originCountry: String? = null
)
