package com.henry.nexus.feature.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.nexus.feature.profile.domain.entity.Profile
import com.henry.nexus.feature.profile.domain.usecase.GetProfileUseCase
import com.henry.nexus.feature.profile.domain.usecase.RefreshProfileUseCase
import com.henry.nexus.feature.profile.domain.usecase.UpdateProfileUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    getProfileUseCase: GetProfileUseCase,
    private val refreshProfileUseCase: RefreshProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val profile: StateFlow<Profile?> = getProfileUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        refreshProfile()
    }

    fun refreshProfile() {
        if (_isLoading.value) return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                refreshProfileUseCase()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                updateProfileUseCase(profile)
            } finally {
                _isLoading.value = false
            }
        }
    }
} 