package io.github.thegbguy.moviescmp.movieDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.thegbguy.moviescmp.network.MovieRepository
import io.github.thegbguy.moviescmp.network.response.MovieDetailsResponse
import kotlinx.coroutines.launch

class MovieDetailsScreenModel(private val repository: MovieRepository) : ScreenModel {
    val movieDetails: MutableState<MovieDetailsResponse?> = mutableStateOf(null)

    fun getMovieDetails(id: Int) = screenModelScope.launch {
        movieDetails.value = repository.getMovieDetails(id)
    }
}