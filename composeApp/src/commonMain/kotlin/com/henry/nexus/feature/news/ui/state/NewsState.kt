package com.henry.nexus.feature.news.ui.state

import com.henry.nexus.feature.news.domain.model.NewsModel

data class NewsState(
    val newsModelItems: List<NewsModel> = emptyList(),
    val isInitialLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isNoMoreData: Boolean = false,
    val error: String? = null,
    val loadMoreError: String? = null,
    val page: Int = 1,
    val pageSize: Int = 10,
    val canLoadMore: Boolean = true,
    val lastLoadTime: Long = 0L,
    val scrollPosition: ScrollPosition = ScrollPosition()
)

data class ScrollPosition(
    val index: Int = 0,
    val offset: Int = 0
) 