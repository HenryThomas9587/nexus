package com.henry.nexus.feature.profile.data.source

import com.henry.nexus.feature.profile.domain.entity.Profile

interface ProfileDataSource {
    suspend fun getProfile(): Profile?
    suspend fun updateProfile(profile: Profile)
} 