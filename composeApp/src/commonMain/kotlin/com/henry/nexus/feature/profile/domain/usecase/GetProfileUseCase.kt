package com.henry.nexus.feature.profile.domain.usecase

import com.henry.nexus.feature.profile.domain.entity.Profile
import com.henry.nexus.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase(private val repository: ProfileRepository) {
    operator fun invoke(): Flow<Profile?> = repository.getProfile()
} 