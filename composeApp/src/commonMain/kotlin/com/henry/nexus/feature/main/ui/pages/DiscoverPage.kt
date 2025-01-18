package com.henry.nexus.feature.main.ui.pages

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.henry.nexus.feature.main.ui.components.DiscoverContent
import com.henry.nexus.feature.main.ui.viewmodel.DiscoverViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiscoverPage(
    paddingValues: PaddingValues,
    viewModel: DiscoverViewModel = koinViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    
    DiscoverContent(
        paddingValues = paddingValues,
        isLoading = isLoading
    )
} 