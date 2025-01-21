package com.henry.nexus.core.navigation

object Route {
    const val MAIN = "main"
    const val HOME = "home"
    const val DISCOVER = "discover"
    const val BOOKMARKS = "bookmarks"
    const val PROFILE = "profile"
    const val NEWS_DETAIL = "news_detail/{newsId}"

    fun newsDetail(newsId: Int) = "news_detail/$newsId"
} 