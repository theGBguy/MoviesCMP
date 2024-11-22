package io.github.thegbguy.moviescmp.network

import io.github.thegbguy.moviescmp.network.response.Movie
import io.github.thegbguy.moviescmp.network.response.MovieDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.invoke

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getMovieDetails(id: Int): MovieDetailsResponse
}


class MovieRepositoryImpl(private val apiClient: ApiClient) : MovieRepository {

    override suspend fun getPopularMovies() = (Dispatchers.IO) {
        apiClient.getPopularMovies().results ?: emptyList()
    }

    override suspend fun getTopRatedMovies() = (Dispatchers.IO) {
        apiClient.getTopRatedMovies().results ?: emptyList()
    }

    override suspend fun getNowPlayingMovies() = (Dispatchers.IO) {
        apiClient.getNowPlayingMovies().results ?: emptyList()
    }

    override suspend fun getUpcomingMovies() = (Dispatchers.IO) {
        apiClient.getUpcomingMovies().results ?: emptyList()
    }

    override suspend fun getMovieDetails(id: Int) = (Dispatchers.IO) {
        apiClient.getMovieDetails(id)
    }
}