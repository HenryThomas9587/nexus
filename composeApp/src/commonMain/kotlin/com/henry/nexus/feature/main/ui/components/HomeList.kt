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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.core.components.LoadingMoreIndicator
import com.henry.nexus.core.util.Debounce
import com.henry.nexus.feature.main.domain.entity.HomeData
import com.henry.nexus.feature.main.domain.entity.HomeItemType
import com.henry.nexus.feature.news.ui.components.StoryItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private const val LOAD_MORE_THRESHOLD = 3
private const val LOAD_MORE_DEBOUNCE = 500L
private const val SCROLL_THRESHOLD = 200  // 滑动多少距离显示按钮

@Composable
fun HomeList(
    modifier: Modifier = Modifier,
    homeDataList: List<HomeData>,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var showButton by remember { mutableStateOf(false) }

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

    //监听滑动距离，显示或隐藏按钮
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    LaunchedEffect(firstVisibleItemIndex) {
        showButton = firstVisibleItemIndex > 0 // 如果第一个可见元素不是顶部，就显示按钮
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
                        homeData.story?.let { StoryItem(story = it) }
                    }
                }
            }
            if (isLoadingMore) {
                item {
                    LoadingMoreIndicator()
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


private fun shouldLoadMore(
    info: LoadMoreInfo,
    isLoadingMore: Boolean
): Boolean {
    return !isLoadingMore &&
            info.totalItemsCount > 0 &&
            info.lastVisibleItem >= info.totalItemsCount - LOAD_MORE_THRESHOLD &&
            !info.isOverscroll
}
