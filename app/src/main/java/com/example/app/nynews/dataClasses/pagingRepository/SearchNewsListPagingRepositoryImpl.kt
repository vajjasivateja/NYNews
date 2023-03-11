package com.example.app.nynews.dataClasses.pagingRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.app.nynews.dataClasses.apiServices.ApiService
import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import com.example.app.nynews.dataClasses.pagingDataSource.SearchNewsListPaginationDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SearchNewsListPagingRepositoryImpl @Inject constructor(
//    private val db:ArticleDatabase,
    private val apiService: ApiService,
) : ImplSearchNewsListPagingRepository {
    override suspend fun getSearchArticlesList(
        startDate: String,
        enddate: String,
        query: String?,
        pageno: Int,
        token: String
    ): Flow<PagingData<ResponseArticlesList.Response.NewsList>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 5, initialLoadSize = 20),
            pagingSourceFactory = {
                SearchNewsListPaginationDataSource(apiService, startDate, enddate,  query, pageno, token)
            }
        ).flow
    }
}


interface ImplSearchNewsListPagingRepository {
    suspend fun getSearchArticlesList(
        startDate: String,
        enddate: String,
        query: String?,
        pageno: Int,
        token: String
    ): Flow<PagingData<ResponseArticlesList.Response.NewsList>>
}