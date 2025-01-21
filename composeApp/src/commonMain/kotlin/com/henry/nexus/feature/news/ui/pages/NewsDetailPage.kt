package com.henry.nexus.feature.news.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.henry.nexus.core.components.ErrorContent
import com.henry.nexus.core.components.LoadingIndicator
import com.henry.nexus.feature.news.domain.model.NewsDetail
import com.henry.nexus.feature.news.ui.viewmodel.NewsDetailViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewsDetailPage(
    newsId: Int,
    navController: NavHostController,
    viewModel: NewsDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("新闻详情") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        }
    ) { innerPaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddingValues)
        ) {
            when {
                state.isLoading -> {
                    LoadingIndicator()
                }

                state.error?.isNotBlank() == true -> {
                    ErrorContent(
                        error = state.error ?: "未知错误",
                        onRetry = viewModel::retry
                    )
                }

                state.newsDetail == null -> {
                    Text(text = "新闻不存在")
                }

                else -> {
                    NewsDetailContent(state.newsDetail!!)
                }
            }
        }
    }
}

@Composable
fun NewsDetailContent(data: NewsDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 图片
        AsyncImage(
            model = data.imageUrl ?: "",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 标题
            Text(
                text = data.title,
                style = MaterialTheme.typography.h5
            )

            // 作者和时间
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = data.author,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "•",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = data.timeAgo,
                    style = MaterialTheme.typography.caption
                )
            }

            // 内容
            Text(
                text = data.content,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
