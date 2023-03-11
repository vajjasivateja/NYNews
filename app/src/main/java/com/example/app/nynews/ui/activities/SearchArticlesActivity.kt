package com.example.app.nynews.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.nynews.dataClasses.Constants
import com.example.app.nynews.dataClasses.model.response.ResponseArticlesList
import com.example.app.nynews.dataClasses.model.response.ResponsePopularArticles
import com.example.app.nynews.dataClasses.pagingViewModel.PopularEmailedNewsListViewModel
import com.example.app.nynews.dataClasses.pagingViewModel.PopularSharedNewsListViewModel
import com.example.app.nynews.dataClasses.pagingViewModel.PopularViewedNewsListViewModel
import com.example.app.nynews.dataClasses.pagingViewModel.SearchNewsListViewModel
import com.example.app.nynews.databinding.ActivitySearchArticlesBinding
import com.example.app.nynews.ui.pagingAdapters.RVPopularArticlesPaginationListAdapter
import com.example.app.nynews.ui.pagingAdapters.RVSearchArticlesPaginationListAdapter
import com.example.app.nynews.ui.pagingAdapters.ReposLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext

@AndroidEntryPoint
class SearchArticlesActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchArticlesBinding
    private val coroutineScope = CoroutineScope(EmptyCoroutineContext)
    private var searchJob: Job? = null
    var keyword = ""
    var pageNo = 1
    private val searchNewsListViewModel: SearchNewsListViewModel by viewModels()
    private val popularViewedNewsListViewModel: PopularViewedNewsListViewModel by viewModels()
    private val popularEmailedNewsListViewModel: PopularEmailedNewsListViewModel by viewModels()
    private val popularSharedNewsListViewModel: PopularSharedNewsListViewModel by viewModels()

    lateinit var rvArticlesPaginationListAdapter: RVSearchArticlesPaginationListAdapter
    lateinit var rvPopularArticlesPaginationListAdapter: RVPopularArticlesPaginationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey("SEARCH")) {
                binding.cvSearchNews.visibility = View.VISIBLE
                supportActionBar!!.title = "Search Articles"
                bindAdapter()
                getSearchArticlesList(keyword, "")
            } else {
                binding.cvSearchNews.visibility = View.GONE
                bindPopularNewsAdapter()
                if (bundle.containsKey("POPULAR_VIEWED")) {
                    getPopularViewedArticlesList()
                    supportActionBar!!.title = "Popular Viewed"
                } else if (bundle.containsKey("POPULAR_SHARED")) {
                    getPopularSharedArticlesList()
                    supportActionBar!!.title = "Popular Shared"
                } else if (bundle.containsKey("POPULAR_EMAILED")) {
                    getPopularEmailedArticlesList()
                    supportActionBar!!.title = "Popular Emailed"
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                keyword = query ?: ""
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    delay(500)
                    getSearchArticlesList(keyword, "")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                keyword = newText ?: ""
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    delay(500)
                    getSearchArticlesList(keyword, "")
                }
                return false
            }
        })
    }

    private fun getSearchArticlesList(keyword: String, sort: String) {
        pageNo = 1
        val access_token = Constants.NEWS_API_KEY
        searchNewsListViewModel.getCompletedList(
            convertDateToFormattedString(getDateOneYearAgo()),
            convertDateToFormattedString(Calendar.getInstance().time),
            if (keyword == "") null else keyword,
            pageNo,
            access_token
        )
        lifecycleScope.launchWhenStarted {
            searchNewsListViewModel.responseCompletedList.collectLatest { response ->
                rvArticlesPaginationListAdapter.submitData(response)
            }
        }
    }


    private fun getPopularEmailedArticlesList() {
        val access_token = Constants.NEWS_API_KEY
        popularEmailedNewsListViewModel.getMostEmailedNewsApi(period = 7, access_token)
        lifecycleScope.launchWhenStarted {
            popularEmailedNewsListViewModel.responsePopularList.collectLatest { response ->
                rvPopularArticlesPaginationListAdapter.submitData(response)
            }
        }
    }

    private fun getPopularSharedArticlesList() {
        val access_token = Constants.NEWS_API_KEY
        popularSharedNewsListViewModel.getMostEmailedNewsApi(period = 7, share_type = "facebook", access_token)
        lifecycleScope.launchWhenStarted {
            popularSharedNewsListViewModel.responsePopularList.collectLatest { response ->
                rvPopularArticlesPaginationListAdapter.submitData(response)
            }
        }
    }

    private fun getPopularViewedArticlesList() {
        val access_token = Constants.NEWS_API_KEY
        popularViewedNewsListViewModel.getMostViewedNewsApi(period = 7, access_token)
        lifecycleScope.launchWhenStarted {
            popularViewedNewsListViewModel.responsePopularList.collectLatest { response ->
                rvPopularArticlesPaginationListAdapter.submitData(response)
            }
        }
    }

    private fun bindPopularNewsAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNews.layoutManager = linearLayoutManager
        rvPopularArticlesPaginationListAdapter = RVPopularArticlesPaginationListAdapter(this)
        binding.rvNews.adapter = rvPopularArticlesPaginationListAdapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { rvPopularArticlesPaginationListAdapter.retry() }
        )
//        binding.rvNews.setHasFixedSize(true)
        rvPopularArticlesPaginationListAdapter.setListener(object : RVPopularArticlesPaginationListAdapter.Listener {
            override fun onArticleSelected(completedTxn: ResponsePopularArticles.Result?) {
                TODO("Not yet implemented")
            }
        })

        rvPopularArticlesPaginationListAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && rvPopularArticlesPaginationListAdapter.itemCount == 0
            if (isListEmpty) {
                Log.d("data", "empty")
            } else {
                Log.d("data", "not empty")
            }
            val canShowShimmer = loadState.source.refresh is LoadState.Loading
            if (canShowShimmer) {
                Log.d("data", "loading")
            } else {
                Log.d("data", "not loading")
            }
        }
    }

    private fun bindAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNews.layoutManager = linearLayoutManager
        rvArticlesPaginationListAdapter = RVSearchArticlesPaginationListAdapter(this)
        binding.rvNews.adapter = rvArticlesPaginationListAdapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { rvArticlesPaginationListAdapter.retry() }
        )
        rvArticlesPaginationListAdapter.setListener(object : RVSearchArticlesPaginationListAdapter.Listener {
            override fun onArticleSelected(article: ResponseArticlesList.Response.NewsList?) {

            }
        })

        rvArticlesPaginationListAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && rvArticlesPaginationListAdapter.itemCount == 0
            if (isListEmpty) {
                Log.d("data", "empty")
            } else {
                Log.d("data", "not empty")
            }
            val canShowShimmer = loadState.source.refresh is LoadState.Loading
            if (canShowShimmer) {
                Log.d("data", "loading")
            } else {
                Log.d("data", "not loading")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun convertDateToFormattedString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun getDateOneYearAgo(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -1)
        return calendar.time
    }
}