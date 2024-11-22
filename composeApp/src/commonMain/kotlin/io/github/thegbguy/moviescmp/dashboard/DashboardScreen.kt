package io.github.thegbguy.moviescmp.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import io.github.thegbguy.moviescmp.Category
import io.github.thegbguy.moviescmp.category.CategoryMoviesScreen
import io.github.thegbguy.moviescmp.movieDetails.MovieDetailsScreen
import io.github.thegbguy.moviescmp.network.response.Movie

class DashboardScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<DashboardScreenModel>()
        val popularMovies by remember { screenModel.popularMovies }
        val nowPlayingMovies by remember { screenModel.nowPlayingMovies }
        val topRatedMovies by remember { screenModel.topRatedMovies }
        val upcomingMovies by remember { screenModel.upcomingMovies }

        MoviesDashboardScreen(
            nowPlayingMovies = nowPlayingMovies,
            topRatedMovies = topRatedMovies,
            popularMovies = popularMovies,
            upcomingMovies = upcomingMovies,
            onSectionClick = { navigator.push(CategoryMoviesScreen(it)) },
            onMovieClick = { navigator.push(MovieDetailsScreen(it.id)) }
        )
    }

    @Composable
    fun MoviesDashboardScreen(
        nowPlayingMovies: List<Movie>,
        topRatedMovies: List<Movie>,
        popularMovies: List<Movie>,
        upcomingMovies: List<Movie>,
        onSectionClick: (Category) -> Unit,
        onMovieClick: (Movie) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Section for Now Playing
            MoviesSection(
                title = "Now Playing",
                movies = nowPlayingMovies,
                onSectionClick = { onSectionClick(Category.NowPlaying) },
                onMovieClick = onMovieClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section for Top Rated
            MoviesSection(
                title = "Top Rated",
                movies = topRatedMovies,
                onSectionClick = { onSectionClick(Category.TopRated) },
                onMovieClick = onMovieClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section for Popular
            MoviesSection(
                title = "Popular",
                movies = popularMovies,
                onSectionClick = { onSectionClick(Category.Popular) },
                onMovieClick = onMovieClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section for Upcoming
            MoviesSection(
                title = "Upcoming",
                movies = upcomingMovies,
                onSectionClick = { onSectionClick(Category.Upcoming) },
                onMovieClick = onMovieClick
            )
        }
    }

    @Composable
    fun MoviesSection(
        title: String,
        movies: List<Movie>,
        onSectionClick: () -> Unit,
        onMovieClick: (Movie) -> Unit
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "See All",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onSectionClick() }
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(movies) { movie ->
                    MovieThumbnail(
                        movie = movie,
                        onClick = { onMovieClick(movie) }
                    )
                }
            }
        }
    }

    @Composable
    fun MovieThumbnail(movie: Movie, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .width(120.dp)
                .clickable { onClick() }
        ) {
            // Movie Poster
            val posterUrl = "https://image.tmdb.org/t/p/w200${movie.posterPath}"
            AsyncImage(
                model = posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Movie Title
            Text(
                text = movie.title ?: "Unknown",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}