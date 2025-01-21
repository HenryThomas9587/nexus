package com.henry.nexus.feature.main.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.henry.nexus.core.navigation.gotoNewsDetail

// 假设这是你的书签数据
private val bookmarks = listOf(
    "Bookmark 1",
    "Bookmark 2",
    "Bookmark 3",
    "Bookmark 4",
    "Bookmark 5",
    "Bookmark 6",
    "Bookmark 7",
    "Bookmark 8",
    "Bookmark 9",
    "Bookmark 10"
)

@Composable
fun BookmarksPage(paddingValues: PaddingValues, navController: NavHostController) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        BookmarkList(bookmarks = bookmarks, navController = navController)
    }
}

@Composable
private fun BookmarkList(bookmarks: List<String>, navController: NavHostController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(bookmarks) { bookmark ->
            BookmarkItem(bookmark = bookmark, onClick = {
                // 这里跳转到详情页， 使用一个固定的 id
                navController.gotoNewsDetail(1)
            })
        }
    }
}

@Composable
private fun BookmarkItem(bookmark: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
            .clickable {
                onClick.invoke()
            }
            .background(MaterialTheme.colors.primary.copy(alpha = 0.3f))
    ) {
        Text(
            text = bookmark,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            // 暂时移除 padding 和 fillMaxSize
            //   modifier = Modifier
            //       .padding(16.dp)
            //     .fillMaxSize()
        )
    }
}