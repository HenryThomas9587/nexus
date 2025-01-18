package com.henry.nexus.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberNavController(): NavController {
    return remember { NavController() }
}

class NavController {
    var currentRoute by mutableStateOf<String>(Route.HOME)
        private set

    fun navigateTo(route: String) {
        currentRoute = route
    }
} 