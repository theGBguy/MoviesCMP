package io.github.thegbguy.moviescmp.category

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.thegbguy.moviescmp.Category
import io.github.thegbguy.moviescmp.network.MovieRepository

class CategoryMoviesScreenModel(
    repository: MovieRepository,
    category: Category
) : ScreenModel {
    val movies = when (category) {
        Category.Popular -> repository.getPaginatedPopularMovies()
        Category.NowPlaying -> repository.getPaginatedNowPlayingMovies()
        Category.Upcoming -> repository.getPaginatedUpcomingMovies()
        Category.TopRated -> repository.getPaginatedTopRatedMovies()
    }.cachedIn(screenModelScope)
}