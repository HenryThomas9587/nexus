package com.henry.nexus.model

data class NewsItem(
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