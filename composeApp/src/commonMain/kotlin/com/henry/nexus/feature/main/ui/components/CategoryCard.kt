package com.henry.nexus.feature.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.henry.nexus.feature.news.domain.model.CategoryModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    categoryModel: CategoryModel,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(80.dp)
            .height(100.dp),
        elevation = 0.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 圆形图标
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = categoryModel.name.firstOrNull()?.toString() ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primary
                )
            }

            // 分类名称
            Text(
                text = categoryModel.name,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
} 