package com.henry.nexus.feature.news.ui.state

import com.henry.nexus.feature.news.domain.model.NewsDetail

data class NewsDetailState(
    val newsDetail: NewsDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 