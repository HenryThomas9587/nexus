package com.henry.nexus.core.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(
    tabItems: List<TabItem>,
    selectedTab: String,
    onNavigate: (TabItem) -> Unit
) {

    BottomNavigation(
        elevation = 1.dp,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        tabItems.forEach { tab ->
            BottomNavigationItem(
                icon = {
                    Icon(tab.icon, contentDescription = null)
                },
                label = { Text(tab.label) },
                selected = selectedTab == tab.route,
                onClick = {
                    onNavigate(tab)
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
