package com.example.app.nynews.dataClasses.pagingRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.app.nynews.dataClasses.apiServices.ApiService
import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import com.example.app.nynews.dataClasses.model.response.ResponsePopularArticles
import com.example.app.nynews.dataClasses.pagingDataSource.PopularSharedNewsListPaginationDataSource
import com.example.app.nynews.dataClasses.pagingDataSource.PopularViewedNewsListPaginationDataSource
import com.example.app.nynews.dataClasses.pagingDataSource.SearchNewsListPaginationDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PopularSharedNewsListPagingRepositoryImpl @Inject constructor(
//    private val db:ArticleDatabase,
    private val apiService: ApiService,
) : ImplPopularSharedNewsListPagingRepository {
    override suspend fun getMostViewedNewsApi(
        period: Int,
        share_type: String,
        token: String
    ): Flow<PagingData<ResponsePopularArticles.Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 5, initialLoadSize = 20),
            pagingSourceFactory = {
                PopularSharedNewsListPaginationDataSource(apiService, period, share_type, token)
            }
        ).flow
    }
}


interface ImplPopularSharedNewsListPagingRepository {
    suspend fun getMostViewedNewsApi(
        period: Int,
        share_type: String,
        token: String
    ): Flow<PagingData<ResponsePopularArticles.Result>>
}