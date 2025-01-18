package com.henry.nexus.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { onNavigate("home") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Search, contentDescription = null) },
            label = { Text("Discover") },
            selected = currentRoute == "discover",
            onClick = { onNavigate("discover") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("Bookmarks") },
            selected = currentRoute == "bookmarks",
            onClick = { onNavigate("bookmarks") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile") }
        )
    }
}