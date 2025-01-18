package com.henry.nexus

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.henry.nexus.di.appModule
import com.henry.nexus.theme.AppTheme
import com.henry.nexus.ui.components.BottomNavBar
import com.henry.nexus.ui.pages.BookmarksPage
import com.henry.nexus.ui.pages.DiscoverPage
import com.henry.nexus.ui.pages.HomePage
import com.henry.nexus.ui.pages.ProfilePage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication


@Composable
@Preview
fun App() {
    var currentRoute by remember { mutableStateOf("home") }
    KoinApplication(application = {
        modules(appModule)
    }) {
        AppTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("趋势") },
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        currentRoute = currentRoute,
                        onNavigate = { route -> currentRoute = route }
                    )
                }
            ) { paddingValues ->
                when (currentRoute) {
                    "home" -> HomePage(paddingValues)
                    "discover" -> DiscoverPage(paddingValues)
                    "bookmarks" -> BookmarksPage(paddingValues)
                    "profile" -> ProfilePage(paddingValues)
                }
            }
        }
    }

}