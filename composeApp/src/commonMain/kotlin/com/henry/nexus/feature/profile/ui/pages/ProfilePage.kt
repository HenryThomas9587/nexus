package com.henry.nexus.feature.profile.ui.pages

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.henry.nexus.feature.profile.ui.components.ProfileContent
import com.henry.nexus.feature.profile.ui.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfilePage(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    
    ProfileContent(
        paddingValues = paddingValues,
        isLoading = isLoading
    )
} 