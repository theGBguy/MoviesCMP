package io.github.thegbguy.moviescmp.movieDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import io.github.thegbguy.moviescmp.network.response.MovieDetailsResponse

data class MovieDetailsScreen(private val id: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<MovieDetailsScreenModel>()

        LaunchedEffect(Unit) {
            screenModel.getMovieDetails(id)
        }

        screenModel.movieDetails.value?.let { movieDetails ->
            MovieDetailsScreen(
                movieDetails = movieDetails,
                onBackClick = { navigator.pop() })
        }
    }
}

@Composable
fun MovieDetailsScreen(movieDetails: MovieDetailsResponse, onBackClick: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .padding(16.dp)
        ) {
            // Backdrop Image
            Box {
                IconButton(
                    modifier = Modifier.align(Alignment.TopStart)
                        .clip(CircleShape)
                        .background(Color.White)
                        .zIndex(1F),
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }

                movieDetails.backdropPath?.let {
                    val backdropUrl = "https://image.tmdb.org/t/p/w780$it"
                    AsyncImage(
                        model = backdropUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Movie Title and Tagline
            Text(
                text = movieDetails.title ?: "Unknown Title",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            movieDetails.tagline?.let {
                Text(
                    text = it,
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )
            }

            // Movie Poster and Details
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                movieDetails.posterPath?.let {
                    val posterUrl = "https://image.tmdb.org/t/p/w200$it"
                    AsyncImage(
                        model = posterUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Release Date: ${movieDetails.releaseDate ?: "N/A"}",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Rating: ${movieDetails.voteAverage ?: "N/A"} / 10",
                        color = Color.Yellow,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Runtime: ${movieDetails.runtime ?: "N/A"} min",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Genres
                    val genres =
                        movieDetails.genres?.filterNotNull()
                            ?.joinToString(", ") { it.name ?: "N/A" }
                    Text(
                        text = "Genres: $genres",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            // Overview
            Text(
                text = "Overview",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = movieDetails.overview ?: "No overview available.",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Additional Details
            Text(
                text = "Additional Information",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Budget: $${movieDetails.budget ?: "N/A"}",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                text = "Revenue: $${movieDetails.revenue ?: "N/A"}",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                text = "IMDB ID: ${movieDetails.imdbId ?: "N/A"}",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
