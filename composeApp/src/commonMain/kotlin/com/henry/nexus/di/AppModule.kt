package com.henry.nexus.di

import com.henry.nexus.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
}

val appModule = module {
    includes(viewModelModule)
}