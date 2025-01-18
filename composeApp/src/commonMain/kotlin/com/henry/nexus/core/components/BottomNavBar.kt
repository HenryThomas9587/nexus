package com.henry.nexus.core.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun BottomNavBar(
    tabItems: List<TabItem>,
    initTab: String,
    onNavigate: (TabItem) -> Unit
) {
    var selectedTab by mutableStateOf(initTab)
    BottomNavigation {
        tabItems.forEach { tab ->
            BottomNavigationItem(
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(tab.label) },
                selected = selectedTab == tab.route,
                onClick = { selectedTab = tab.route; onNavigate(tab) }
            )
        }
    }
}