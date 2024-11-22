package io.github.thegbguy.moviescmp.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.thegbguy.moviescmp.network.MovieRepository
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
        popularMovies.value = repository.getPopularMovies().take(10)
    }

    private fun getTopRatedMovies() = screenModelScope.launch {
        topRatedMovies.value = repository.getTopRatedMovies().take(10)
    }

    private fun getNowPlayingMovies() = screenModelScope.launch {
        nowPlayingMovies.value = repository.getNowPlayingMovies().take(10)
    }

    private fun getUpcomingMovies() = screenModelScope.launch {
        upcomingMovies.value = repository.getUpcomingMovies().take(10)
    }

}