package com.henry.nexus.feature.news.domain.model

data class NewsModel(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
    val author: String,
    val timeAgo: String,
    val views: String,
    val comments: Int,
    val imageUrl: String?
) 