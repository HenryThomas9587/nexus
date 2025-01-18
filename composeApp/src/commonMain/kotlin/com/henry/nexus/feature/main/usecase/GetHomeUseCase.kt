package com.henry.nexus.feature.main.usecase

import com.henry.nexus.feature.main.domain.entity.HomeData
import com.henry.nexus.feature.main.domain.entity.HomeItemType
import com.henry.nexus.feature.news.domain.repository.CategoryRepository
import com.henry.nexus.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetHomeUseCase(
    private val newsRepository: NewsRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend fun getHomeData(page: Int, pageSize: Int, categoryId: Int): List<HomeData> =
        coroutineScope {
            if (page == 1) {
                val tradedNewsDeferred = async { newsRepository.getTradedNews(page, pageSize) }
                val categoryDeferred =
                    async { categoryRepository.getRecommendedCategories(page, pageSize) }
                val newsByCategoryDeferred =
                    async { newsRepository.getNewsByCategory(page, pageSize, categoryId) }
                val tradedNews = tradedNewsDeferred.await()
                val category = categoryDeferred.await()
                val newsByCategory = newsByCategoryDeferred.await()
                val result = mutableListOf(
                    HomeData(
                        id = 1,
                        title = HomeItemType.TRADE_NEWS.displayName,
                        type = HomeItemType.TRADE_NEWS.type,
                        tradeNews = tradedNews
                    ),
                    HomeData(
                        id = 2,
                        title = HomeItemType.CATEGORY.displayName,
                        type = HomeItemType.CATEGORY.type,
                        categories = category
                    ),

                    )
                result.addAll(newsByCategory.map {
                    HomeData(
                        id = it.id,
                        title = HomeItemType.STORY.displayName,
                        type = HomeItemType.STORY.type,
                        story = it
                    )
                })
                result
            } else {
                newsRepository.getNews(page, pageSize).map {
                    HomeData(
                        id = it.id,
                        title = HomeItemType.STORY.displayName,
                        type = HomeItemType.STORY.type,
                        story = it
                    )
                }
            }
        }
}