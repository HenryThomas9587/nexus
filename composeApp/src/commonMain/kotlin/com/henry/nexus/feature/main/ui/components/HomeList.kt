package com.henry.nexus.feature.main.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.core.components.LoadingIndicator
import com.henry.nexus.core.util.Debounce
import com.henry.nexus.feature.main.domain.entity.HomeData
import com.henry.nexus.feature.main.domain.entity.HomeItemType
import com.henry.nexus.feature.news.ui.components.NewsItem
import com.henry.nexus.feature.news.ui.components.StoryItem
import kotlinx.coroutines.flow.distinctUntilChanged

private const val LOAD_MORE_THRESHOLD = 3
private const val LOAD_MORE_DEBOUNCE = 500L

@Composable
fun HomeList(
    modifier: Modifier = Modifier,
    homeDataList: List<HomeData>,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // 加载更多防抖
    val loadMoreDebounce = remember(coroutineScope) {
        Debounce(
            delayMillis = LOAD_MORE_DEBOUNCE,
            coroutineScope = coroutineScope
        ) {
            onLoadMore()
        }
    }

    // 监听加载更多
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            LoadMoreInfo(
                lastVisibleItem = lastVisibleItem,
                totalItemsCount = totalItemsCount,
                isOverscroll = listState.canScrollForward
            )
        }
            .distinctUntilChanged()
            .collect { info ->
                if (shouldLoadMore(
                        info = info,
                        isLoadingMore = isLoadingMore
                    )
                ) {
                    loadMoreDebounce.debounce()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(
            items = homeDataList,
            key = { "${it.type}-${it.id}" }
        ) { homeData ->
            when (homeData.type) {
                HomeItemType.TRADE_NEWS.type -> {
                    TradeNewsSection(
                        title = homeData.title,
                        tradeNews = homeData.tradeNews ?: emptyList()
                    )
                }

                HomeItemType.CATEGORY.type -> {
                    CategorySection(
                        title = homeData.title,
                        categories = homeData.categories ?: emptyList()
                    )
                }

                HomeItemType.STORY.type -> {
                    homeData.story?.let { StoryItem(story = it, onClick = {}) }
                }
            }
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    LoadingIndicator()
                }
            }
        }
    }
}

private data class LoadMoreInfo(
    val lastVisibleItem: Int,
    val totalItemsCount: Int,
    val isOverscroll: Boolean
)

private fun shouldLoadMore(
    info: LoadMoreInfo,
    isLoadingMore: Boolean
): Boolean {
    return !isLoadingMore &&
            info.totalItemsCount > 0 &&
            info.lastVisibleItem >= info.totalItemsCount - LOAD_MORE_THRESHOLD &&
            !info.isOverscroll
} 