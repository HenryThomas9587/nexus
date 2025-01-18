package com.henry.nexus.feature.main.ui.pages

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.henry.nexus.feature.main.ui.components.HomeContent
import com.henry.nexus.feature.main.ui.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomePage(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = koinViewModel()
) {
    val homeDataList by viewModel.homeDataList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()

    HomeContent(
        paddingValues = paddingValues,
        homeDataList = homeDataList,
        isRefreshing = isRefreshing,
        isLoadingMore = isLoadingMore,
        onRefresh = viewModel::refresh,
        onLoadMore = viewModel::loadMore
    )
} 