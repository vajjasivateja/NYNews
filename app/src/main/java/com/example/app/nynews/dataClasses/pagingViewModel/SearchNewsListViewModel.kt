package com.example.app.nynews.dataClasses.pagingViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.app.nynews.DispatcherProvider
import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import com.example.app.nynews.dataClasses.pagingRepository.ImplSearchNewsListPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsListViewModel @Inject constructor(
    private val searchNewsListPagingRepository: ImplSearchNewsListPagingRepository,
    @ApplicationContext private val context: Context,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val responseCompletedList = MutableStateFlow<PagingData<ResponseArticlesList.Response.NewsList>>(PagingData.empty())
    fun getCompletedList(
        startDate: String,
        enddate: String,
        query: String?,
        pageno: Int,
        token: String
    ) = viewModelScope.launch(dispatchers.io) {
        responseCompletedList.value = PagingData.empty()
        searchNewsListPagingRepository.getSearchArticlesList(startDate, enddate, query, pageno, token).collect { values ->
            responseCompletedList.value = values
        }
    }
}