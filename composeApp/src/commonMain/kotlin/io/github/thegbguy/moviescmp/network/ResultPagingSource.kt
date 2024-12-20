package io.github.thegbguy.moviescmp.network

import androidx.paging.PagingSource
import androidx.paging.PagingState

open class ResultPagingSource<T : Any>(
    private val pagingData: suspend (page: Int, pageSize: Int) -> Result<List<T>>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
        (params.key ?: 1).let { page ->
            try {
                pagingData(page, params.loadSize)
                    .run {
                        when (this) {
                            /* success */
                            is Result.Success -> {
                                LoadResult.Page(
                                    data = value,
                                    /* no previous pagination int as page */
                                    prevKey = page.takeIf { it > 1 }?.dec(),
                                    /* no pagination if no results found else next page as +1 */
                                    nextKey = page.takeIf { value.size >= params.loadSize }?.inc()
                                )
                            }
                            /* error */
                            is Error -> LoadResult.Error(this)
                            else -> LoadResult.Error(IllegalStateException("$this type of [Result] is not allowed here"))
                        }
                    }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
}