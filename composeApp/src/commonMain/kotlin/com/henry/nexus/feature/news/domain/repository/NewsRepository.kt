package com.henry.nexus.feature.news.domain.repository

import com.henry.nexus.feature.news.domain.model.NewsModel

interface NewsRepository {
    suspend fun getNews(page: Int, pageSize: Int): List<NewsModel>
    suspend fun getTradedNews(page: Int, pageSize: Int): List<NewsModel>
    suspend fun getHotNews(page: Int, pageSize: Int): List<NewsModel>
    suspend fun getLatestNews(page: Int, pageSize: Int): List<NewsModel>
    suspend fun getNewsByCategory(page: Int, pageSize: Int, categoryId: Int): List<NewsModel>
}