package com.henry.nexus.feature.main.ui.components

data class LoadMoreInfo(
    val lastVisibleItem: Int,
    val totalItemsCount: Int,
    val isOverscroll: Boolean
)