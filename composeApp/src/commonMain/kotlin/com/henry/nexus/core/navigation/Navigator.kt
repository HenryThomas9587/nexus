package com.henry.nexus.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.henry.nexus.feature.main.ui.pages.DiscoverPage
import com.henry.nexus.feature.main.ui.pages.HomePage
import com.henry.nexus.feature.profile.ui.pages.ProfilePage

@Composable
fun NavigationHost(
    navController: NavController,
    paddingValues: PaddingValues
) {
    when (navController.currentRoute) {
        Route.HOME -> HomePage(paddingValues)
        Route.DISCOVER -> DiscoverPage(paddingValues)
        Route.PROFILE -> ProfilePage(paddingValues)
        else -> HomePage(paddingValues)
    }
} 