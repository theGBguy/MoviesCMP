package io.github.thegbguy.moviescmp.network

sealed interface Result<out T> {
    class Success<out T>(val value: T) : Result<T>
    data object Loading : Result<Nothing>
    class Error(val throwable: Throwable) : Result<Nothing>
}

inline fun <T, R> Result<T>.map(transform: (value: T) -> R): Result<R> =
    when (this) {
        is Result.Success -> Result.Success(transform(value))
        is Result.Error -> Result.Error(throwable)
        is Result.Loading -> Result.Loading
    }