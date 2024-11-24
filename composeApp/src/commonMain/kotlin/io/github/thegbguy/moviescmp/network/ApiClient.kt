package io.github.thegbguy.moviescmp.network

import io.github.thegbguy.moviescmp.BuildKonfig
import io.github.thegbguy.moviescmp.network.response.MovieDetailsResponse
import io.github.thegbguy.moviescmp.network.response.NowPlayingMoviesResponse
import io.github.thegbguy.moviescmp.network.response.PopularMoviesResponse
import io.github.thegbguy.moviescmp.network.response.TopRatedMoviesResponse
import io.github.thegbguy.moviescmp.network.response.UpcomingMoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient {
    private val baseUrl = "https://api.themoviedb.org/3"
    private val client = HttpClient {
        expectSuccess = true
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        BuildKonfig.ACCESS_TOKEN,
                        null
                    )
                }
            }
        }
    }

    suspend fun getPopularMovies(): PopularMoviesResponse {
        val response = client.get("$baseUrl/movie/popular?language=en-US&page=1")
        return response.body()
    }

    suspend fun getNowPlayingMovies(): NowPlayingMoviesResponse {
        val response = client.get("$baseUrl/movie/now_playing?language=en-US&page=1")
        return response.body()
    }

    suspend fun getTopRatedMovies(): TopRatedMoviesResponse {
        val response = client.get("$baseUrl/movie/top_rated?language=en-US&page=1")
        return response.body()
    }

    suspend fun getUpcomingMovies(): UpcomingMoviesResponse {
        val response = client.get("$baseUrl/movie/upcoming?language=en-US&page=1")
        return response.body()
    }

    suspend fun getMovieDetails(id: Int): MovieDetailsResponse {
        val response = client.get("$baseUrl/movie/$id?language=en-US")
        return response.body()
    }
}