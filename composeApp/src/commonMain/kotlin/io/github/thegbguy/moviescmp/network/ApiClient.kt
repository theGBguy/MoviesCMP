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
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
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

    suspend fun getPopularMovies(page: Int): Result<PopularMoviesResponse> {
        return client.call {
            url("$baseUrl/movie/popular?language=en-US&page=$page")
            method = HttpMethod.Get
        }
    }

    suspend fun getNowPlayingMovies(page: Int): Result<NowPlayingMoviesResponse> {
        return client.call {
            url("$baseUrl/movie/now_playing?language=en-US&page=$page")
            method = HttpMethod.Get
        }
    }

    suspend fun getTopRatedMovies(page: Int): Result<TopRatedMoviesResponse> {
        return client.call {
            url("$baseUrl/movie/top_rated?language=en-US&page=$page")
            method = HttpMethod.Get
        }
    }

    suspend fun getUpcomingMovies(page: Int): Result<UpcomingMoviesResponse> {
        return client.call {
            url("$baseUrl/movie/upcoming?language=en-US&page=$page")
            method = HttpMethod.Get
        }
    }

    suspend fun getMovieDetails(id: Int): Result<MovieDetailsResponse> {
        return client.call {
            url("$baseUrl/movie/$id?language=en-US")
            method = HttpMethod.Get
        }
    }

    private suspend inline fun <reified T> HttpClient.call(
        block: HttpRequestBuilder.() -> Unit
    ): Result<T> = try {
        val response = request(block)
        if (response.status == HttpStatusCode.OK) {
            Result.Success(response.body())
        } else {
            Result.Error(Throwable("${response.status}: ${response.bodyAsText()}"))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}