package com.henry.nexus.feature.news.domain.model

data class NewsDetail (
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val description: String,
    val author: String,
    val imageUrl:String?,
    val timeAgo: String,
    val views: String,
    val comments: String
)
