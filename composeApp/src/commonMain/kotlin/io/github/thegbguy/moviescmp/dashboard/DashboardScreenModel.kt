package io.github.thegbguy.moviescmp.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.thegbguy.moviescmp.network.MovieRepository
import io.github.thegbguy.moviescmp.network.Result
import io.github.thegbguy.moviescmp.network.response.Movie
import kotlinx.coroutines.launch

class DashboardScreenModel(private val repository: MovieRepository) : ScreenModel {
    val popularMovies: MutableState<List<Movie>> = mutableStateOf(emptyList())
    val topRatedMovies: MutableState<List<Movie>> = mutableStateOf(emptyList())
    val nowPlayingMovies: MutableState<List<Movie>> = mutableStateOf(emptyList())
    val upcomingMovies: MutableState<List<Movie>> = mutableStateOf(emptyList())

    init {
        getPopularMovies()
        getTopRatedMovies()
        getNowPlayingMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() = screenModelScope.launch {
        popularMovies.value = when (val result = repository.getPopularMovies()) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
            Result.Loading -> emptyList()
        }
    }

    private fun getTopRatedMovies() = screenModelScope.launch {
        topRatedMovies.value = when (val result = repository.getTopRatedMovies()) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
            Result.Loading -> emptyList()
        }
    }

    private fun getNowPlayingMovies() = screenModelScope.launch {
        nowPlayingMovies.value = when (val result = repository.getNowPlayingMovies()) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
            Result.Loading -> emptyList()
        }
    }

    private fun getUpcomingMovies() = screenModelScope.launch {
        upcomingMovies.value = when (val result = repository.getUpcomingMovies()) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
            Result.Loading -> emptyList()
        }
    }

}