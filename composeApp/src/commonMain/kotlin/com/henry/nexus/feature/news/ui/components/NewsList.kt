package com.henry.nexus.feature.news.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.core.components.LoadMoreErrorItem
import com.henry.nexus.core.components.NoMoreDataItem
import com.henry.nexus.core.util.Debounce
import com.henry.nexus.core.components.LoadingIndicator
import com.henry.nexus.feature.news.domain.model.NewsModel
import com.henry.nexus.feature.news.ui.state.ScrollPosition
import kotlinx.coroutines.flow.distinctUntilChanged

private const val LOAD_MORE_THRESHOLD = 3
private const val LOAD_MORE_DEBOUNCE = 500L
private const val SCROLL_POSITION_DEBOUNCE = 300L
private const val ITEM_KEY_PREFIX = "news_"

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    newsModelItems: List<NewsModel>,
    isLoadingMore: Boolean,
    loadMoreError: String?,
    isNoMoreData: Boolean,
    canLoadMore: Boolean,
    scrollPosition: ScrollPosition,
    onScrollPositionChange: (ScrollPosition) -> Unit,
    onLoadMore: () -> Unit,
    onRetryLoadMore: () -> Unit,
    onNewsClick: (NewsModel) -> Unit
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition.index,
        initialFirstVisibleItemScrollOffset = scrollPosition.offset
    )
    val coroutineScope = rememberCoroutineScope()
    // 滚动位置防抖
    val scrollDebounce = remember(coroutineScope) {
        Debounce(
            delayMillis = SCROLL_POSITION_DEBOUNCE,
            coroutineScope = coroutineScope
        ) {
            onScrollPositionChange(
                ScrollPosition(
                    index = listState.firstVisibleItemIndex,
                    offset = listState.firstVisibleItemScrollOffset
                )
            )
        }
    }

    // 加载更多防抖
    val loadMoreDebounce = remember(coroutineScope) {
        Debounce(
            delayMillis = LOAD_MORE_DEBOUNCE,
            coroutineScope = coroutineScope
        ) {
            onLoadMore()
        }
    }

    // 监听滚动位置变化
    LaunchedEffect(listState) {
        snapshotFlow {
            ScrollPosition(
                index = listState.firstVisibleItemIndex,
                offset = listState.firstVisibleItemScrollOffset
            )
        }
            .distinctUntilChanged()
            .collect {
                scrollDebounce.debounce()
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
                        isLoadingMore = isLoadingMore,
                        hasError = loadMoreError != null,
                        isNoMoreData = isNoMoreData,
                        canLoadMore = canLoadMore
                    )
                ) {
                    loadMoreDebounce.debounce()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = newsModelItems,
            key = { "${ITEM_KEY_PREFIX}${it.id}" },
            contentType = { "news_item" }
        ) { newsItem ->
            NewsItemWrapper(
                newsModel = newsItem,
                onClick = { onNewsClick(newsItem) }
            )
        }

        when {
            loadMoreError != null -> {
                item(
                    key = "load_more_error",
                    contentType = "load_more_error"
                ) {
                    LoadMoreErrorItem(
                        error = loadMoreError,
                        onRetry = onRetryLoadMore
                    )
                }
            }

            isLoadingMore -> {
                item(
                    key = "loading_more",
                    contentType = "loading"
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        LoadingIndicator()
                    }
                }
            }

            isNoMoreData -> {
                item(
                    key = "no_more_data",
                    contentType = "no_more_data"
                ) {
                    NoMoreDataItem()
                }
            }
        }
    }
}

@Composable
private fun NewsItemWrapper(
    newsModel: NewsModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    key(newsModel.id) {
        NewsItem(
            newsModel = newsModel,
            onClick = onClick,
            modifier = modifier
        )
    }
}

private data class LoadMoreInfo(
    val lastVisibleItem: Int,
    val totalItemsCount: Int,
    val isOverscroll: Boolean
)

private fun shouldLoadMore(
    info: LoadMoreInfo,
    isLoadingMore: Boolean,
    hasError: Boolean,
    isNoMoreData: Boolean,
    canLoadMore: Boolean
): Boolean {
    return !isLoadingMore &&
            !hasError &&
            !isNoMoreData &&
            canLoadMore &&
            info.totalItemsCount > 0 &&
            info.lastVisibleItem >= info.totalItemsCount - LOAD_MORE_THRESHOLD &&
            !info.isOverscroll
} 