package com.henry.nexus.core.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NavController {
    var currentRoute by mutableStateOf(Route.HOME)
        private set
    
    private val backStack = mutableListOf<String>()

    fun navigateTo(route: String) {
        backStack.add(currentRoute)
        currentRoute = route
    }

    fun navigateBack() {
        if (backStack.isNotEmpty()) {
            currentRoute = backStack.removeLast()
        }
    }

    fun canNavigateBack(): Boolean = backStack.isNotEmpty()
}

