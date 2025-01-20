package com.henry.nexus.feature.main.ui.components

import NewsItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
    Column(modifier = modifier.padding(top = 8.dp)) {
        Text(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = title,
            style = MaterialTheme.typography.h6,
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp ),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = tradeNews,
                key = { it.id }
            ) { news ->
                NewsItem(newsModel = news, onClick = {})
            }
        }
    }
} 