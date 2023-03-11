package com.example.app.nynews.diModule

import com.example.app.nynews.dataClasses.pagingRepository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideArticlesPagingRepository(userRepository: SearchNewsListPagingRepositoryImpl): ImplSearchNewsListPagingRepository

    @Binds
    abstract fun provideSharedPagingRepository(userRepository: PopularSharedNewsListPagingRepositoryImpl): ImplPopularSharedNewsListPagingRepository

    @Binds
    abstract fun provideEmailedPagingRepository(userRepository: PopularEmailedNewsListPagingRepositoryImpl): ImplPopularEmailedNewsListPagingRepository

    @Binds
    abstract fun provideViewedPagingRepository(userRepository: PopularViewedNewsListPagingRepositoryImpl): ImplPopularViewedNewsListPagingRepository
}