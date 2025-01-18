package com.henry.nexus.feature.profile.domain.usecase

import com.henry.nexus.feature.profile.domain.entity.Profile
import com.henry.nexus.feature.profile.domain.repository.ProfileRepository

class UpdateProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(profile: Profile) = repository.updateProfile(profile)
} 