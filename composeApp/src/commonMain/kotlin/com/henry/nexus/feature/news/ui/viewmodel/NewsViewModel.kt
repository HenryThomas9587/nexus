package com.henry.nexus.feature.news.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.nexus.feature.news.domain.repository.NewsRepository
import com.henry.nexus.feature.news.ui.state.NewsState
import com.henry.nexus.feature.news.ui.state.ScrollPosition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isInitialLoading = true, error = null) }
            try {
                val result = newsRepository.getNews(1, state.value.pageSize)
                _state.update {
                    it.copy(
                        newsModelItems = result,
                        isInitialLoading = false,
                        isNoMoreData = result.isEmpty(),
                        page = if (result.isEmpty()) 1 else 2
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isInitialLoading = false,
                        error = e.message ?: "加载失败"
                    )
                }
            }
        }
    }

    fun refresh() {
        val currentState = state.value
        if (currentState.isRefreshing || currentState.isInitialLoading) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true,
                    error = null,
                    canLoadMore = false,
                    scrollPosition = ScrollPosition() // 重置滚动位置
                )
            }

            try {
                val result = newsRepository.getNews(1, currentState.pageSize)
                _state.update {
                    it.copy(
                        newsModelItems = result,
                        isRefreshing = false,
                        isNoMoreData = result.isEmpty(),
                        page = if (result.isEmpty()) 1 else 2,
                        loadMoreError = null,
                        canLoadMore = true,  // 刷新完成后恢复加载更多
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isRefreshing = false,
                        error = e.message ?: "刷新失败",
                        canLoadMore = true  // 刷新失败后恢复加载更多
                    )
                }
            }
        }
    }

    fun loadMore() {
        val currentState = state.value
        if (!shouldLoadMore(currentState)) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingMore = true,
                    loadMoreError = null
                )
            }

            try {
                val result = newsRepository.getNews(currentState.page, currentState.pageSize)
                _state.update {
                    it.copy(
                        newsModelItems = it.newsModelItems + result,
                        isLoadingMore = false,
                        isNoMoreData = result.isEmpty(),
                        page = if (result.isEmpty()) it.page else it.page + 1,
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoadingMore = false,
                        loadMoreError = e.message ?: "加载更多失败"
                    )
                }
            }
        }
    }

    private fun shouldLoadMore(state: NewsState): Boolean {
        return !state.isLoadingMore &&
                !state.isNoMoreData &&
                state.loadMoreError == null &&
                state.canLoadMore &&
                !state.isRefreshing &&
                !state.isInitialLoading
    }

    fun retryInitialLoad() {
        loadInitialData()
    }

    fun retryLoadMore() {
        loadMore()
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun updateScrollPosition(position: ScrollPosition) {
        _state.update { it.copy(scrollPosition = position) }
    }
} 