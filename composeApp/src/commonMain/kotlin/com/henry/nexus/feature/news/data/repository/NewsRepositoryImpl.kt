package com.henry.nexus.feature.news.data.repository

import com.henry.nexus.feature.news.data.source.NewsDataSource
import com.henry.nexus.feature.news.domain.model.NewsModel
import com.henry.nexus.feature.news.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val remoteDataSource: NewsDataSource
) : NewsRepository {

    override suspend fun getNews(page: Int, pageSize: Int): List<NewsModel> {
        return remoteDataSource.getNews(page, pageSize)
    }

    override suspend fun getTradedNews(page: Int, pageSize: Int): List<NewsModel> {
        return remoteDataSource.getTradedNews(page, pageSize)
    }

    override suspend fun getHotNews(page: Int, pageSize: Int): List<NewsModel> {
        return remoteDataSource.getNews(page, pageSize)
    }

    override suspend fun getLatestNews(page: Int, pageSize: Int): List<NewsModel> {
        return remoteDataSource.getNews(page, pageSize)
    }

    override suspend fun getNewsByCategory(
        page: Int,
        pageSize: Int,
        categoryId: Int
    ): List<NewsModel> {
        return remoteDataSource.getNewsByCategory(page, pageSize, categoryId)
    }
} 