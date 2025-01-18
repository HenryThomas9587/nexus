package com.henry.nexus.feature.profile.data.repository

import com.henry.nexus.feature.profile.data.source.ProfileDataSource
import com.henry.nexus.feature.profile.domain.entity.Profile
import com.henry.nexus.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileRepositoryImpl(
    private val dataSource: ProfileDataSource
) : ProfileRepository {
    private val _profile = MutableStateFlow<Profile?>(null)

    override fun getProfile(): Flow<Profile?> = _profile

    override suspend fun refreshProfile() {
        _profile.value = dataSource.getProfile()
    }

    override suspend fun updateProfile(profile: Profile) {
        dataSource.updateProfile(profile)
        _profile.value = profile
    }
} 