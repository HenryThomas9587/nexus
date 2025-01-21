package com.henry.nexus.feature.news.data.source

import com.henry.nexus.feature.news.domain.model.NewsDetail
import com.henry.nexus.feature.news.domain.model.NewsModel

interface NewsDataSource {
    suspend fun getNews(page: Int, pageSize: Int): List<NewsModel>
    suspend fun getTradedNews(page: Int, pageSize: Int): List<NewsModel>
    suspend fun getNewsByCategory(page: Int, pageSize: Int, categoryId: Int): List<NewsModel>
    suspend fun getNewsDetail(newsId: Int): NewsDetail?
}