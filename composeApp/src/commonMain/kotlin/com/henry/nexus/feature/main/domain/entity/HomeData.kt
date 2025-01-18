package com.henry.nexus.feature.main.domain.entity

import com.henry.nexus.feature.news.domain.model.CategoryModel
import com.henry.nexus.feature.news.domain.model.NewsModel

enum class HomeItemType(val type: Int, val displayName: String) {
    TRADE_NEWS(1, "Trade News"),
    CATEGORY(2, " Categories"),
    STORY(3, "Story"),
}

data class HomeData(
    val id: Int,
    val title: String,
    val type: Int,
    val tradeNews: List<NewsModel>? = null,
    val categories: List<CategoryModel>? = null,
    val story: NewsModel? = null
)