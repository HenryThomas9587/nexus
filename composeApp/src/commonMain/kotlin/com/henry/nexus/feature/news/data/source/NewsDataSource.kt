package com.henry.nexus.feature.news.data.source

import com.henry.nexus.feature.news.domain.model.NewsModel

interface NewsDataSource {
    suspend fun getNews(page: Int, pageSize: Int): List<NewsModel>
}