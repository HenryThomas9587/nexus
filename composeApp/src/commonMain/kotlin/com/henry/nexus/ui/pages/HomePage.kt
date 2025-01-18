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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.ui.components.NewsCard
import com.henry.nexus.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(
    paddingValues: PaddingValues,
    viewModel : HomeViewModel = koinViewModel(),
) {
    // 收集状态流
    val newsItems by viewModel.newsItems.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    
    val coroutineScope = rememberCoroutineScope()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // 列表滚动状态
        val listState = rememberLazyListState()
        
        // 显示回到顶部按钮的条件：第一个可见项的索引大于5
        val showScrollToTop by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 5
            }
        }
        
        // 下拉刷新状态
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = { viewModel.refresh() }
        )
        
        // 检测是否需要加载更多
        // 当最后可见项接近列表末尾时触发加载
        val loadMore = remember {
            derivedStateOf {
                val layoutInfo = listState.layoutInfo
                val totalItemsNumber = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = 
                    (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
                // 当最后可见项距离底部小于3项时触发加载
                lastVisibleItemIndex > (totalItemsNumber - 3)
            }
        }

        // 监听加载更多状态并触发加载
        LaunchedEffect(loadMore.value) {
            if (loadMore.value && !isLoadingMore && !isRefreshing) {
                viewModel.loadMore()
            }
        }

        // 下拉刷新容器
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            // 新闻列表
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                // 新闻项列表
                items(
                    items = newsItems,
                    // 使用id作为key以优化重组
                    key = { it.id }
                ) { newsItem ->
                    key(newsItem.id) {
                        NewsCard(
                            newsItem = newsItem,
                            onClick = { /* 处理点击事件 */ }
                        )
                    }
                }

                // 加载更多指示器
                if (isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primary,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }

            // 下拉刷新指示器
            // 始终显示在列表顶部
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.primary
            )
            
            // 回到顶部按钮
            AnimatedVisibility(
                visible = showScrollToTop,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 72.dp,
                        end = 16.dp
                    ),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(56.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 12.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Scroll to top",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}