package io.github.thegbguy.moviescmp.category

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.thegbguy.moviescmp.Category
import io.github.thegbguy.moviescmp.network.MovieRepository
import io.github.thegbguy.moviescmp.network.response.Movie
import kotlinx.coroutines.launch

class CategoryMoviesScreenModel(private val repository: MovieRepository) : ScreenModel {
    val movies: MutableState<List<Movie>> = mutableStateOf(emptyList())

    fun getMoviesByCategory(category: Category) = screenModelScope.launch {
        movies.value = when (category) {
            Category.Popular -> repository.getPopularMovies()
            Category.NowPlaying -> repository.getNowPlayingMovies()
            Category.Upcoming -> repository.getUpcomingMovies()
            Category.TopRated -> repository.getTopRatedMovies()
        }
    }
}