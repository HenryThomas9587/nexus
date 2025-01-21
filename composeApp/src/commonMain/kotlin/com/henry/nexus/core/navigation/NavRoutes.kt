package com.henry.nexus.core.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.henry.nexus.core.navigation.NavRoutes.newsDetail
import com.henry.nexus.feature.main.ui.pages.BookmarksPage
import com.henry.nexus.feature.main.ui.pages.DiscoverPage
import com.henry.nexus.feature.main.ui.pages.HomePage
import com.henry.nexus.feature.news.ui.pages.NewsDetailPage
import com.henry.nexus.feature.profile.ui.pages.ProfilePage

object NavRoutes {
    const val HOME = "Home"
    const val DISCOVER = "Discover"
    const val BOOKMARKS = "Bookmarks"
    const val PROFILE = "Profile"
    const val NEWS_DETAIL = "newsDetail"

    fun newsDetail(newsId: Int) = "$NEWS_DETAIL/$newsId"

    const val newsDetailRoute = "$NEWS_DETAIL/{newsId}"

    val newsDetailArguments = listOf(navArgument("newsId") {
        type = NavType.IntType
    })

    fun navigateTo(navController: NavHostController, route: String) {
        navController.navigate(route) {
            // 处理回退栈
            navController.graph.startDestinationRoute?.let {
                popUpTo(it) {
                    saveState = true
                }
            }
            //栈顶则复用
            launchSingleTop = true
            // 跳转之后会恢复保存的之前页面状态
            restoreState = true
        }
    }
}


// 在你的 NavRoutes.kt 文件中定义此扩展函数，或者放在一个合适的地方。
fun NavHostController.gotoNewsDetail(id: Int) {
    NavRoutes.navigateTo(this, newsDetail(id))
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,
    ) {
        composable(NavRoutes.HOME) { HomePage(paddingValues, navController) }
        composable(NavRoutes.DISCOVER) { DiscoverPage(paddingValues) }
        composable(NavRoutes.BOOKMARKS) { BookmarksPage(paddingValues, navController) }
        composable(NavRoutes.PROFILE) { ProfilePage(paddingValues) }
        composable(
            route = NavRoutes.newsDetailRoute,
            arguments = NavRoutes.newsDetailArguments
        ) {
            val newsId = it.arguments?.getInt("newsId") ?: 0
            NewsDetailPage(newsId, navController)
        }
    }
}
