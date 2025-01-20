package com.henry.nexus.feature.main.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.core.components.LoadMoreErrorItem
import com.henry.nexus.core.components.LoadingMoreIndicator
import com.henry.nexus.core.components.NoMoreDataItem
import com.henry.nexus.core.util.Debounce
import com.henry.nexus.feature.main.domain.entity.HomeData
import com.henry.nexus.feature.main.domain.entity.HomeItemType
import com.henry.nexus.feature.main.ui.pages.ScrollPosition
import com.henry.nexus.feature.news.ui.components.StoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private const val LOAD_MORE_THRESHOLD = 3
private const val LOAD_MORE_DEBOUNCE = 500L
private const val SCROLL_POSITION_DEBOUNCE = 300L

@Composable
fun HomeListNew(
    modifier: Modifier = Modifier,
    homeDataList: List<HomeData>,
    isLoadingMore: Boolean,
    loadMoreError: String?,
    isNoMoreData: Boolean,
    canLoadMore: Boolean,
    scrollPosition: ScrollPosition,
    onScrollPositionChange: (ScrollPosition) -> Unit,
    onLoadMore: () -> Unit,
    onRetryLoadMore: () -> Unit,
    onClick: (HomeData) -> Unit
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition.index,
        initialFirstVisibleItemScrollOffset = scrollPosition.offset
    )
    val coroutineScope = rememberCoroutineScope()
    var showButton by remember { mutableStateOf(false) }

    HomeListScrollHandler(
        coroutineScope = coroutineScope,
        listState = listState,
        isLoadingMore = isLoadingMore,
        canLoadMore = canLoadMore,
        onLoadMore = onLoadMore,
        onShowButtonChange = { showButton = it }
    )

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


    LaunchedEffect(listState) {
        snapshotFlow {
            Pair(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset
            )
        }
            .distinctUntilChanged()
            .collect { (_, _) ->
                scrollDebounce.debounce()
            }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 72.dp) // 预留按钮空间
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
                        homeData.story?.let {
                            StoryItem(
                                story = it,
                                onClick = { onClick(homeData) })
                        }
                    }
                }
            }

            item(
                key = "load_more_error",
                contentType = "load_more_error"
            ) {
                if (loadMoreError != null) {
                    LoadMoreErrorItem(
                        error = loadMoreError,
                        onRetry = onRetryLoadMore
                    )
                }
            }
            item(key = "loading_more", contentType = "loading") {
                if (isLoadingMore) {
                    LoadingMoreIndicator()
                }
            }
            item(key = "no_more_data", contentType = "no_more_data") {
                if (isNoMoreData) {
                    NoMoreDataItem()
                }
            }
        }
        //回到顶部按钮
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            FloatingActionButton(onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            }, modifier = Modifier.size(50.dp)) {
                Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Back to top")
            }
        }
    }
}


@Composable
private fun HomeListScrollHandler(
    coroutineScope: CoroutineScope,
    listState: LazyListState,
    isLoadingMore: Boolean,
    canLoadMore: Boolean,
    onLoadMore: () -> Unit,
    onShowButtonChange: (Boolean) -> Unit
) {
    // 加载更多防抖
    val loadMoreDebounce = remember(coroutineScope) {
        Debounce(
            delayMillis = LOAD_MORE_DEBOUNCE,
            coroutineScope = coroutineScope
        ) {
            onLoadMore()
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            Triple(lastVisibleItemIndex, totalItemsCount, listState.canScrollForward)
        }
            .distinctUntilChanged()
            .collect { (lastVisibleItemIndex, totalItemsCount, isOverscroll) ->
                val shouldLoadMore = canLoadMore &&
                        !isLoadingMore &&
                        totalItemsCount > 0 &&
                        lastVisibleItemIndex >= totalItemsCount - LOAD_MORE_THRESHOLD &&
                        !isOverscroll
                if (shouldLoadMore) {
                    loadMoreDebounce.debounce()
                }
                onShowButtonChange(listState.firstVisibleItemIndex > 0)
            }
    }
}