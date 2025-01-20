package com.henry.nexus.feature.main.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.henry.nexus.core.components.EmptyContent
import com.henry.nexus.core.components.ErrorContent
import com.henry.nexus.core.components.InitialLoadingContent
import com.henry.nexus.feature.main.ui.components.HomeListNew
import com.henry.nexus.feature.main.ui.viewmodel.HomeNewViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(
    paddingValues: PaddingValues,
    viewModel: HomeNewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = viewModel::refresh
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .pullRefresh(pullRefreshState)
    ) {
        when {
            state.isInitialLoading -> {
                InitialLoadingContent()
            }

            state.error != null -> {
                ErrorContent(
                    error = state.error ?: "未知错误",
                    onRetry = viewModel::retryInitialLoad
                )
            }

            state.items.isEmpty() -> {
                EmptyContent()
            }

            else -> {
                HomeListNew(
                    homeDataList = state.items,
                    isLoadingMore = state.isLoadingMore,
                    loadMoreError = state.loadMoreError,
                    isNoMoreData = state.isNoMoreData,
                    canLoadMore = state.canLoadMore,
                    scrollPosition = state.scrollPosition,
                    onScrollPositionChange = viewModel::updateScrollPosition,
                    onLoadMore = viewModel::loadMore,
                    onRetryLoadMore = viewModel::retryLoadMore,
                    onClick = { /* 处理新闻点击事件 */ }
                )
                PullRefreshIndicator(
                    refreshing = state.isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.primary
                )   // do nothing
            }
        }
    }
} 