package com.henry.nexus.feature.profile.domain.repository

import com.henry.nexus.feature.profile.domain.entity.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<Profile?>
    suspend fun refreshProfile()
    suspend fun updateProfile(profile: Profile)
} 