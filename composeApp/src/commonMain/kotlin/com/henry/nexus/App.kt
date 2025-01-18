package com.henry.nexus

import androidx.compose.runtime.Composable
import com.henry.nexus.di.appModule
import com.henry.nexus.theme.AppTheme
import com.henry.nexus.ui.pages.MainPage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        AppTheme {
            MainPage()
        }
    }
}