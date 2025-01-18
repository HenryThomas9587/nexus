package com.henry.nexus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.henry.nexus.model.NewsItem
import kotlin.random.Random

class HomeViewModel : ViewModel() {
    private val _newsItems = MutableStateFlow<List<NewsItem>>(emptyList())
    val newsItems: StateFlow<List<NewsItem>> = _newsItems.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10
    private var hasMoreData = true

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        currentPage = 0
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val items = generateMockData(0)
                _newsItems.value = items
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun refresh() {
        if (_isRefreshing.value) return
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                delay(1000) // 模拟网络延迟
                currentPage = 0
                val items = generateMockData(0)
                _newsItems.value = items
                hasMoreData = true
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun loadMore() {
        if (_isLoadingMore.value || !hasMoreData) return
        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                delay(1000) // 模拟网络延迟
                val nextPage = currentPage + 1
                val newItems = generateMockData(nextPage)
                if (newItems.isNotEmpty()) {
                    _newsItems.value = _newsItems.value + newItems
                    currentPage = nextPage
                } else {
                    hasMoreData = false
                }
            } finally {
                _isLoadingMore.value = false
            }
        }
    }

    private fun generateMockData(page: Int): List<NewsItem> {
        val startId = page * pageSize
        return (startId until startId + pageSize).map { id ->
            val categories = listOf("sports", "technology", "politics", "entertainment", "science")
            val authors = listOf("Sarah Brown", "David Wilson", "Emily Johnson", "Michael Brown", "Jessica Lee")
            NewsItem(
                id = id,
                title = "新闻标题 ${id + 1} - ${categories.random().capitalize()}领域的最新进展",
                category = categories.random(),
                description = "这是关于${categories.random()}领域的详细新闻内容...",
                author = authors.random(),
                timeAgo = "${Random.nextInt(1, 30)}d ago",
                views = "${Random.nextInt(1, 20)}.$${Random.nextInt(1, 9)}k",
                comments = Random.nextInt(100, 500),
                imageUrl = "https://picsum.photos/800/400?random=${id + 1}"
            )
        }
    }
}

private fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }