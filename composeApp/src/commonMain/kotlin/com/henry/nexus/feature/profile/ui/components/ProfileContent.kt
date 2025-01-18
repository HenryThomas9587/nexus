package com.henry.nexus.feature.profile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.henry.nexus.core.components.LoadingIndicator

@Composable
fun ProfileContent(
    paddingValues: PaddingValues,
    isLoading: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "个人资料页面 - 待实现",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground
            )
            
            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                LoadingIndicator()
            }
        }
    }
} 