package com.henry.nexus.feature.news.data.source

import com.henry.nexus.feature.news.domain.model.NewsModel
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockNewsDataSource : NewsDataSource {
    override suspend fun getNews(page: Int, pageSize: Int): List<NewsModel> {
        delay(1000) // 模拟网络延迟
        return generateMockNews(page)
    }

    private fun generateMockNews(page: Int): List<NewsModel> {
        val startIndex = page * PAGE_SIZE
        return (startIndex until startIndex + PAGE_SIZE).map { index ->
            NewsModel(
                id = index,
                title = "新闻标题 $index",
                category = "分类 ${index % 5}",
                description = "这是新闻描述 $index",
                author = "作者 ${index % 3}",
                timeAgo = "${index % 24}小时前",
                views = "${Random.nextInt(100, 10000)}",
                comments ="${Random.nextInt(100, 10000)}",
                imageUrl = if (index % 2 == 0) "https://picsum.photos/300/200?random=$index" else null
            )
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
} 