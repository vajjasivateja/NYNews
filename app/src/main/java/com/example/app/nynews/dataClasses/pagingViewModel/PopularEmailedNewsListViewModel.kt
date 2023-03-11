package com.example.app.nynews.dataClasses.pagingViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.app.nynews.DispatcherProvider
import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import com.example.app.nynews.dataClasses.model.response.ResponsePopularArticles
import com.example.app.nynews.dataClasses.pagingRepository.ImplPopularEmailedNewsListPagingRepository
import com.example.app.nynews.dataClasses.pagingRepository.ImplPopularViewedNewsListPagingRepository
import com.example.app.nynews.dataClasses.pagingRepository.ImplSearchNewsListPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularEmailedNewsListViewModel @Inject constructor(
    private val popularEmailedNewsListPagingRepository: ImplPopularEmailedNewsListPagingRepository,
    @ApplicationContext private val context: Context,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val responsePopularList = MutableStateFlow<PagingData<ResponsePopularArticles.Result>>(PagingData.empty())
    fun getMostEmailedNewsApi(
        period: Int,
        token: String
    ) = viewModelScope.launch(dispatchers.io) {
        responsePopularList.value = PagingData.empty()
        popularEmailedNewsListPagingRepository.getMostEmailedNewsApi(period, token).collect { values ->
            responsePopularList.value = values
        }
    }
}