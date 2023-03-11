package com.example.app.nynews.dataClasses.pagingDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.app.nynews.dataClasses.apiServices.ApiService
import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import com.example.app.nynews.dataClasses.model.response.ResponsePopularArticles
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PopularEmailedNewsListPaginationDataSource @Inject constructor(
    private val apiService: ApiService,
    val period: Int,
    private val token: String,
) : PagingSource<Int, ResponsePopularArticles.Result>() {

    override fun getRefreshKey(state: PagingState<Int, ResponsePopularArticles.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponsePopularArticles.Result> {
        val position = params.key ?: 1
        return try {
            val response = apiService.getMostEmailedNewsApi(period, token)
            if (response.isSuccessful) {
                if (response.body() != null && response.body()!!.status == "OK" && response.body()!!.results != null) {
                    val repos = response.body()!!.results
                    return if (repos != null) {
                        val nextKey = if (repos.isEmpty()) null else position + 1
                        val prevKey = if (position == 1) null else position - 1
                        LoadResult.Page(data = repos, prevKey = prevKey, nextKey = nextKey)
                    } else {
                        LoadResult.Error(Exception("Response data is null"))
                    }
                } else {
                    LoadResult.Error(Exception(response.message()))
                }
            } else {
                try {
                    LoadResult.Error(HttpException(response))
                } catch (exception: IOException) {
                    LoadResult.Error(exception)
                }
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}