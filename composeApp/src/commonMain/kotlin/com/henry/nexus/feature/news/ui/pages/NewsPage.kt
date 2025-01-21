package com.henry.nexus.feature.news.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.henry.nexus.core.components.EmptyContent
import com.henry.nexus.core.components.ErrorContent
import com.henry.nexus.core.components.InitialLoadingContent
import com.henry.nexus.feature.news.ui.components.NewsList
import com.henry.nexus.feature.news.ui.viewmodel.NewsViewModel
import org.koin.compose.viewmodel.koinViewModel
import com.henry.nexus.core.navigation.NavController
import com.henry.nexus.core.navigation.Route

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsPage(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: NewsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = viewModel::refresh
    )

    // 显示错误信息
    state.error?.let { error ->
        LaunchedEffect(error) {
            snackBarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
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

            state.newsModelItems.isEmpty() -> {
                EmptyContent()
            }

            else -> {
                NewsList(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState),
                    newsModelItems = state.newsModelItems,
                    isLoadingMore = state.isLoadingMore,
                    loadMoreError = state.loadMoreError,
                    isNoMoreData = state.isNoMoreData,
                    canLoadMore = state.canLoadMore,
                    scrollPosition = state.scrollPosition,
                    onScrollPositionChange = viewModel::updateScrollPosition,
                    onLoadMore = viewModel::loadMore,
                    onRetryLoadMore = viewModel::retryLoadMore,
                    onNewsClick = { news -> 
                        navController.navigateTo(Route.newsDetail(news.id))
                    }
                )

                PullRefreshIndicator(
                    refreshing = state.isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.primary
                )
            }
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
} 