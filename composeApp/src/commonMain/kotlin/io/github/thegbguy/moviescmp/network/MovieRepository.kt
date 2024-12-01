package io.github.thegbguy.moviescmp.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.thegbguy.moviescmp.network.response.Movie
import io.github.thegbguy.moviescmp.network.response.MovieDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.invoke

interface MovieRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getTopRatedMovies(): Result<List<Movie>>
    suspend fun getNowPlayingMovies(): Result<List<Movie>>
    suspend fun getUpcomingMovies(): Result<List<Movie>>
    fun getPaginatedPopularMovies(): Flow<PagingData<Movie>>
    fun getPaginatedTopRatedMovies(): Flow<PagingData<Movie>>
    fun getPaginatedNowPlayingMovies(): Flow<PagingData<Movie>>
    fun getPaginatedUpcomingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(id: Int): Result<MovieDetailsResponse>
}


class MovieRepositoryImpl(private val apiClient: ApiClient) : MovieRepository {

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return apiClient.getPopularMovies(1).map { it.results?.take(10) ?: emptyList() }
    }

    override fun getPaginatedPopularMovies() = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            ResultPagingSource { page, _ ->
                apiClient.getPopularMovies(page).map { it.results ?: emptyList() }
            }
        }
    ).flow

    override suspend fun getTopRatedMovies(): Result<List<Movie>> {
        return apiClient.getTopRatedMovies(1).map { it.results?.take(10) ?: emptyList() }
    }

    override fun getPaginatedTopRatedMovies() = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            ResultPagingSource { page, _ ->
                apiClient.getTopRatedMovies(page).map { it.results ?: emptyList() }
            }
        }
    ).flow

    override suspend fun getNowPlayingMovies(): Result<List<Movie>> {
        return apiClient.getNowPlayingMovies(1).map { it.results?.take(10) ?: emptyList() }
    }

    override fun getPaginatedNowPlayingMovies() = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            ResultPagingSource { page, _ ->
                apiClient.getNowPlayingMovies(page).map { it.results ?: emptyList() }
            }
        }
    ).flow

    override suspend fun getUpcomingMovies(): Result<List<Movie>> {
        return apiClient.getUpcomingMovies(1).map { it.results?.take(10) ?: emptyList() }
    }

    override fun getPaginatedUpcomingMovies() = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            ResultPagingSource { page, _ ->
                apiClient.getUpcomingMovies(page).map { it.results ?: emptyList() }
            }
        }
    ).flow

    override suspend fun getMovieDetails(id: Int) = (Dispatchers.IO) {
        apiClient.getMovieDetails(id).map { it }
    }
}