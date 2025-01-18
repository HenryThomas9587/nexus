package com.henry.nexus.feature.main.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.henry.nexus.feature.main.ui.pages.BookmarksPage
import com.henry.nexus.feature.main.ui.pages.DiscoverPage
import com.henry.nexus.feature.main.ui.pages.HomePage
import com.henry.nexus.feature.profile.ui.pages.ProfilePage

object Router {
    object Route {
        const val HOME = "Home"
        const val DISCOVER = "Discover"
        const val BOOKMARKS = "Bookmarks"
        const val PROFILE = "Profile"
    }

    // 方案1: 使用 remember
    @Composable
    fun buildComposeRoute(route: String, paddingValues: PaddingValues) {
        val pages = remember {
            mapOf<String, @Composable (PaddingValues) -> Unit>(
                Route.HOME to { padding -> HomePage(padding) },
                Route.DISCOVER to { padding -> DiscoverPage(padding) },
                Route.BOOKMARKS to { padding -> BookmarksPage(padding) },
                Route.PROFILE to { padding -> ProfilePage(padding) }
            )
        }
        pages[route]?.invoke(paddingValues)
    }
}

// 方案2: 使用类型别名和顶层映射
private typealias ComposableScreen = @Composable (PaddingValues) -> Unit

object RouterAlternative {
    object Route {
        const val HOME = "Home"
        const val DISCOVER = "Discover"
        const val BOOKMARKS = "Bookmarks"
        const val PROFILE = "Profile"
    }

    private val pages: Map<String, ComposableScreen> = mapOf(
        Route.HOME to { padding -> HomePage(padding) },
        Route.DISCOVER to { padding -> DiscoverPage(padding) },
        Route.BOOKMARKS to { padding -> BookmarksPage(padding) },
        Route.PROFILE to { padding -> ProfilePage(padding) }
    )

    @Composable
    fun buildComposeRoute(route: String, paddingValues: PaddingValues) {
        pages[route]?.invoke(paddingValues)
    }
}