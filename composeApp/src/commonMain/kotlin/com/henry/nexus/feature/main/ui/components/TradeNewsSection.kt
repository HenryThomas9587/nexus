package com.henry.nexus.feature.main.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.feature.news.domain.model.NewsModel

@Composable
fun TradeNewsSection(
    modifier: Modifier = Modifier,
    title: String,
    tradeNews: List<NewsModel>
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = tradeNews,
                key = { it.id }
            ) { news ->
                TradeNewsCard(newsModel = news)
            }
        }
    }
} 