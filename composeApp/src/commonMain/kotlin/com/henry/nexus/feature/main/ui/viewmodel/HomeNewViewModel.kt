package com.henry.nexus.feature.main.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.nexus.feature.main.ui.pages.HomeState
import com.henry.nexus.feature.main.ui.pages.ScrollPosition
import com.henry.nexus.feature.main.usecase.GetHomeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeNewViewModel(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()
    private val categoryId = 0
    private var page = 1
    private val pageSize = 10

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        page = 1
        viewModelScope.launch {
            _state.update { it.copy(isInitialLoading = true, error = null) }
            try {
                val result =
                    getHomeUseCase.getHomeData(page, pageSize, categoryId)
                _state.update {
                    it.copy(
                        items = result,
                        isInitialLoading = false,
                        isNoMoreData = result.isEmpty()
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
                val result =
                    getHomeUseCase.getHomeData(1, pageSize, categoryId)
                _state.update {
                    it.copy(
                        items = result,
                        isRefreshing = false,
                        isNoMoreData = result.isEmpty(),
                        loadMoreError = null,
                        canLoadMore = true,  // 刷新完成后恢复加载更多
                    )
                }
                page = 1
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
                val result = getHomeUseCase.getHomeData(
                    page + 1,
                    pageSize,
                    categoryId
                )
                _state.update {
                    it.copy(
                        items = it.items + result,
                        isLoadingMore = false,
                        isNoMoreData = result.isEmpty(),
                    )
                }
                page++
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

    private fun shouldLoadMore(state: HomeState): Boolean {
        return !state.isLoadingMore &&
                !state.isNoMoreData &&
                state.loadMoreError.isNullOrBlank() &&
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