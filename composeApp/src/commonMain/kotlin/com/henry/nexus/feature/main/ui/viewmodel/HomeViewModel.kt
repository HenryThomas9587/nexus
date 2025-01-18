package com.henry.nexus.feature.main.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.nexus.feature.main.domain.entity.HomeData
import com.henry.nexus.feature.main.usecase.GetHomeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private val _homeDataList = MutableStateFlow<List<HomeData>>(emptyList())
    val homeDataList: StateFlow<List<HomeData>> = _homeDataList.asStateFlow()

    private var page = 1
    private val pageSize = 10

    private val category = 0

    init {
        refresh()
    }

    fun refresh() {
        page = 1
        if (_isRefreshing.value) return
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                _homeDataList.value = getHomeUseCase.getHomeData(page, pageSize, 0)
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun loadMore() {
        if (_isLoadingMore.value) return
        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                val result = getHomeUseCase.getHomeData(page + 1, pageSize, category)
                _homeDataList.value += result
                page++
            } finally {
                _isLoadingMore.value = false
            }
        }
    }
}