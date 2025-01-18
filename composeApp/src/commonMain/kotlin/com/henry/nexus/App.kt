package com.henry.nexus

import androidx.compose.runtime.Composable
import com.henry.nexus.core.di.appModule
import com.henry.nexus.feature.main.di.mainModule
import com.henry.nexus.feature.main.ui.pages.MainPage
import com.henry.nexus.feature.news.di.newsModule
import com.henry.nexus.feature.profile.di.profileModule
import com.henry.nexus.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule, newsModule, profileModule, mainModule)
    }) {
        AppTheme {
            MainPage()
        }
    }
}