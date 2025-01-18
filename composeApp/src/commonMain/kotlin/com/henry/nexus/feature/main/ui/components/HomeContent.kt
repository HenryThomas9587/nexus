package com.henry.nexus.feature.main.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.henry.nexus.feature.main.domain.entity.HomeData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    homeDataList: List<HomeData>,
    isRefreshing: Boolean,
    isLoadingMore: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .pullRefresh(pullRefreshState)
    ) {
        HomeList(
            homeDataList = homeDataList,
            isLoadingMore = isLoadingMore,
            onLoadMore = onLoadMore
        )

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary
        )
    }
} 