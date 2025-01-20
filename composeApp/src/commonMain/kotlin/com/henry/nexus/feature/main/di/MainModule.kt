package com.henry.nexus.feature.main.di

import com.henry.nexus.feature.main.ui.viewmodel.DiscoverViewModel
import com.henry.nexus.feature.main.ui.viewmodel.HomeNewViewModel
import com.henry.nexus.feature.main.ui.viewmodel.HomeViewModel
import com.henry.nexus.feature.main.ui.viewmodel.MainViewModel
import com.henry.nexus.feature.main.usecase.GetHomeUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { GetHomeUseCase(get(), get()) }
    viewModel { HomeNewViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DiscoverViewModel() }
    viewModel { MainViewModel() }
}