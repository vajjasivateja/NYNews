package com.example.app.nynews.dataClasses.pagingDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.app.nynews.dataClasses.apiServices.ApiService
import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class SearchNewsListPaginationDataSource @Inject constructor(
    private val apiService: ApiService,
    val startDate: String,
    val enddate: String,
    val query: String?,
    val pageno: Int,
    private val token: String,
) : PagingSource<Int, ResponseArticlesList.Response.NewsList>() {

    override fun getRefreshKey(state: PagingState<Int, ResponseArticlesList.Response.NewsList>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseArticlesList.Response.NewsList> {
        val position = params.key ?: 1
        return try {
            val response = apiService.getSearchNewsApi(startDate, enddate, query, pageno, token)
            if (response.isSuccessful) {
                if (response.body() != null && response.body()!!.status == "OK" && response.body()!!.response.docs != null) {
                    val repos = response.body()!!.response.docs
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