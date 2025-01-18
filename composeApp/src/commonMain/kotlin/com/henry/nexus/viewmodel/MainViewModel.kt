package com.henry.nexus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.nexus.model.TabItemModel
import com.henry.nexus.ui.components.TabItem
import com.henry.nexus.ui.util.TabUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _tabItems = MutableStateFlow<List<TabItem>>(emptyList())
    val tabItems: StateFlow<List<TabItem>> = _tabItems.asStateFlow()

    init {
        fetchTabItems()
    }

    private fun fetchTabItems() {
        viewModelScope.launch {
            delay(1000)
            _tabItems.value = mockTabItems().map { TabUtil.convert(it) }
        }
    }

    private fun mockTabItems(): List<TabItemModel> {
        return listOf(
            TabItemModel("Home", "Home", "Home"),
            TabItemModel("Discover", "Discover", "Discover"),
            TabItemModel("Bookmarks", "Bookmarks", "Bookmarks"),
            TabItemModel("Profile", "Profile", "Profile")
        )
    }
}