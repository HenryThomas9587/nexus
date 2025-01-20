package com.henry.nexus.feature.main.ui.pages

import com.henry.nexus.feature.main.domain.entity.HomeData

data class HomeState(
    val items: List<HomeData> = emptyList(),
    val isInitialLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isNoMoreData: Boolean = false,
    val error: String? = null,
    val loadMoreError: String? = null,
    val canLoadMore: Boolean = true,
    val scrollPosition: ScrollPosition = ScrollPosition()
)

data class ScrollPosition(
    val index: Int = 0,
    val offset: Int = 0
)