package com.henry.nexus.feature.profile.domain.usecase

import com.henry.nexus.feature.profile.domain.repository.ProfileRepository

class RefreshProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke() = repository.refreshProfile()
} 