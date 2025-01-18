package com.henry.nexus.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.model.NewsItem
import com.henry.nexus.ui.components.NewsCard
import com.henry.nexus.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

private object HomePageDefaults {
    const val SCROLL_TO_TOP_THRESHOLD = 5
    const val LOAD_MORE_THRESHOLD = 3
    val CONTENT_PADDING = 8.dp
    val LOADING_INDICATOR_PADDING = 24.dp
    val LOADING_INDICATOR_STROKE = 2.dp
    val SCROLL_TO_TOP_BUTTON_SIZE = 56.dp
    val SCROLL_TO_TOP_ICON_SIZE = 32.dp
    val SCROLL_TO_TOP_BOTTOM_PADDING = 72.dp
    val SCROLL_TO_TOP_END_PADDING = 16.dp
    val FAB_DEFAULT_ELEVATION = 6.dp
    val FAB_PRESSED_ELEVATION = 12.dp
}

@Composable
fun HomePage(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val newsItems by viewModel.newsItems.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    HomePageContent(
        paddingValues = paddingValues,
        newsItems = newsItems,
        isRefreshing = isRefreshing,
        isLoadingMore = isLoadingMore,
        listState = listState,
        onRefresh = viewModel::refresh,
        onLoadMore = viewModel::loadMore,
        coroutineScope = coroutineScope
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomePageContent(
    paddingValues: PaddingValues,
    newsItems: List<NewsItem>,
    isRefreshing: Boolean,
    isLoadingMore: Boolean,
    listState: LazyListState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    coroutineScope: CoroutineScope
) {
    val showScrollToTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > HomePageDefaults.SCROLL_TO_TOP_THRESHOLD
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex > (totalItemsNumber - HomePageDefaults.LOAD_MORE_THRESHOLD)
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !isLoadingMore && !isRefreshing) {
            onLoadMore()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        NewsList(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            paddingValues = paddingValues,
            newsItems = newsItems,
            isLoadingMore = isLoadingMore,
            listState = listState
        )

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary
        )

        Box(modifier = Modifier.matchParentSize()) {
            ScrollToTopButton(
                visible = showScrollToTop,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            )
        }
    }
}

@Composable
private fun NewsList(
    modifier: Modifier,
    paddingValues: PaddingValues,
    newsItems: List<NewsItem>,
    isLoadingMore: Boolean,
    listState: LazyListState
) {
    LazyColumn(
        state = listState,
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(vertical = HomePageDefaults.CONTENT_PADDING)
    ) {
        items(
            items = newsItems,
            key = { it.id }
        ) { newsItem ->
            NewsCard(
                newsItem = newsItem,
                onClick = { /* 处理点击事件 */ }
            )
        }

        if (isLoadingMore) {
            item {
                LoadingIndicator()
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(HomePageDefaults.LOADING_INDICATOR_PADDING),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            strokeWidth = HomePageDefaults.LOADING_INDICATOR_STROKE
        )
    }
}

@Composable
private fun ScrollToTopButton(
    visible: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = HomePageDefaults.SCROLL_TO_TOP_BOTTOM_PADDING,
                end = HomePageDefaults.SCROLL_TO_TOP_END_PADDING
            )
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomEnd) // 将按钮对齐到右下角
        ) {
            FloatingActionButton(
                onClick = onClick,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(HomePageDefaults.SCROLL_TO_TOP_BUTTON_SIZE),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = HomePageDefaults.FAB_DEFAULT_ELEVATION,
                    pressedElevation = HomePageDefaults.FAB_PRESSED_ELEVATION
                )
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Scroll to top",
                    modifier = Modifier.size(HomePageDefaults.SCROLL_TO_TOP_ICON_SIZE)
                )
            }
        }
    }
}
