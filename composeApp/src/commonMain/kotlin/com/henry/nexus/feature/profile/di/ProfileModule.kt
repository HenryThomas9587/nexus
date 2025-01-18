package com.henry.nexus.feature.profile.di

import com.henry.nexus.feature.profile.data.repository.ProfileRepositoryImpl
import com.henry.nexus.feature.profile.data.source.MockProfileDataSource
import com.henry.nexus.feature.profile.data.source.ProfileDataSource
import com.henry.nexus.feature.profile.domain.repository.ProfileRepository
import com.henry.nexus.feature.profile.domain.usecase.GetProfileUseCase
import com.henry.nexus.feature.profile.domain.usecase.RefreshProfileUseCase
import com.henry.nexus.feature.profile.domain.usecase.UpdateProfileUseCase
import com.henry.nexus.feature.profile.ui.viewmodel.ProfileViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    // Data
    single<ProfileDataSource> { MockProfileDataSource() }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    
    // Use Cases
    singleOf(::GetProfileUseCase)
    singleOf(::RefreshProfileUseCase)
    singleOf(::UpdateProfileUseCase)
    
    // ViewModel
    viewModel { ProfileViewModel(get(), get(), get()) }
} 