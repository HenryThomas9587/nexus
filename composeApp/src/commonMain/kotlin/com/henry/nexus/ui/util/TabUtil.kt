package com.henry.nexus.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.henry.nexus.model.TabItemModel
import com.henry.nexus.ui.components.TabItem

object TabUtil {

    private val mapTab = mapOf(
        "Home" to Icons.Filled.Home,
        "Discover" to Icons.Filled.Search,
        "Bookmarks" to Icons.Filled.Favorite,
        "Profile" to Icons.Filled.Person
    )

    private fun getIcon(tabName: String): ImageVector {
        return mapTab[tabName] ?: Icons.Filled.Home
    }

    fun convert(model: TabItemModel): TabItem {
        return TabItem(
            route = model.route,
            label = model.label,
            icon = getIcon(model.label)
        )
    }
}