package io.github.thegbguy.moviescmp.category

import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.thegbguy.moviescmp.Category
import io.github.thegbguy.moviescmp.network.MovieRepository
import io.github.thegbguy.moviescmp.network.response.Movie
import kotlinx.coroutines.flow.flowOf

class CategoryMoviesScreenModel(private val repository: MovieRepository) : ScreenModel {
    var moviesPagingDataFlow = flowOf(PagingData.empty<Movie>())

    fun getMoviesByCategory(category: Category) {
        moviesPagingDataFlow = when (category) {
            Category.Popular -> repository.getPaginatedPopularMovies()
            Category.NowPlaying -> repository.getPaginatedNowPlayingMovies()
            Category.Upcoming -> repository.getPaginatedUpcomingMovies()
            Category.TopRated -> repository.getPaginatedTopRatedMovies()
        }.cachedIn(screenModelScope)
    }
}