package com.henry.nexus.feature.main.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.feature.news.domain.model.CategoryModel

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    title: String,
    categories: List<CategoryModel>,
    onCategoryClick: (CategoryModel) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
            
            Text(
                text = "查看全部",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primary
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = categories,
                key = { it.id }
            ) { category ->
                CategoryCard(
                    categoryModel = category,
                    onClick = { onCategoryClick(category) }
                )
            }
        }
    }
} 