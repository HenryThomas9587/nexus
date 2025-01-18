package com.henry.nexus.feature.main.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.henry.nexus.feature.news.domain.model.NewsModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TradeNewsCard(
    modifier: Modifier = Modifier,
    newsModel: NewsModel,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .height(200.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        onClick = onClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 图片区域
            AsyncImage(
                model = newsModel.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            // 文字区域
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // 标题
                Text(
                    text = newsModel.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 底部信息
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 作者
                    Text(
                        text = newsModel.author,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )

                    // 时间
                    Text(
                        text = newsModel.timeAgo,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
} 