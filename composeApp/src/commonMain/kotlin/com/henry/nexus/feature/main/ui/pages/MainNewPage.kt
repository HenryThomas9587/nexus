package com.henry.nexus.feature.main.ui.pages

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.henry.nexus.core.components.BottomNavBar
import com.henry.nexus.core.navigation.NavHostContainer
import com.henry.nexus.core.navigation.NavRoutes
import com.henry.nexus.feature.main.ui.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNewPage(viewModel: MainViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val tabItems by viewModel.tabItems.collectAsState()

    // 获取当前导航的路由状态
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentRoute = currentDestination?.route ?: NavRoutes.HOME

    var selectedTab by remember { mutableStateOf(currentRoute) }
    val showBottomBar = currentDestination.isTopLevelDestinationIn(
        setOf(NavRoutes.HOME, NavRoutes.DISCOVER, NavRoutes.BOOKMARKS, NavRoutes.PROFILE)
    )

    LaunchedEffect(currentRoute) {
        selectedTab = currentRoute
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    tabItems = tabItems,
                    selectedTab = selectedTab,
                    onNavigate = { tabItem ->
                        if (selectedTab == tabItem.route) return@BottomNavBar
                        selectedTab = tabItem.route
                        NavRoutes.navigateTo(navController, tabItem.route)
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHostContainer(navController, paddingValues)
    }
}


fun NavDestination?.isTopLevelDestinationIn(routes : Set<String>): Boolean {
    if(this == null) return false
    return  routes.contains(route)
}
