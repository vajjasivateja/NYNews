package com.example.app.nynews.dataClasses.apiServices

import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import com.example.app.nynews.dataClasses.model.response.ResponsePopularArticles
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("search/v2/articlesearch.json")
    suspend fun getSearchNewsApi(
        @Query("begin_date") beginDate: String,
        @Query("end_date") endDate: String,
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("api-key") api_key: String,
    ): Response<ResponseArticlesList>

    @GET("mostpopular/v2/emailed/{period}.json")
    suspend fun getMostEmailedNewsApi(
        @Path("period") period: Int,
        @Query("api-key") api_key: String
    ): Response<ResponsePopularArticles>

    @GET("mostpopular/v2/shared/{period}/{share_type}.json")
    suspend fun getMostSharedNewsApi(
        @Path("period") period: Int,
        @Path("share_type") share_type: String = "facebook",
        @Query("api-key") api_key: String
    ): Response<ResponsePopularArticles>

    @GET("mostpopular/v2/viewed/{period}.json")
    suspend fun getMostViewedNewsApi(
        @Path("period") period: Int,
        @Query("api-key") api_key: String
    ): Response<ResponsePopularArticles>
}