package io.github.thegbguy.moviescmp.di

import io.github.thegbguy.moviescmp.category.CategoryMoviesScreenModel
import io.github.thegbguy.moviescmp.dashboard.DashboardScreenModel
import io.github.thegbguy.moviescmp.movieDetails.MovieDetailsScreenModel
import io.github.thegbguy.moviescmp.network.ApiClient
import io.github.thegbguy.moviescmp.network.MovieRepository
import io.github.thegbguy.moviescmp.network.MovieRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single { ApiClient() }
    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
    factory { DashboardScreenModel(get()) }
    factory { params -> CategoryMoviesScreenModel(get(), params.get()) }
    factory { MovieDetailsScreenModel(get()) }
}