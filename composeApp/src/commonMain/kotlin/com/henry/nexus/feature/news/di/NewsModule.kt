package com.henry.nexus.feature.news.di

import com.henry.nexus.feature.news.data.repository.CategoryRepositoryImpl
import com.henry.nexus.feature.news.data.repository.NewsRepositoryImpl
import com.henry.nexus.feature.news.data.source.MockNewsDataSource
import com.henry.nexus.feature.news.data.source.NewsDataSource
import com.henry.nexus.feature.news.domain.repository.CategoryRepository
import com.henry.nexus.feature.news.domain.repository.NewsRepository
import com.henry.nexus.feature.news.ui.viewmodel.NewsDetailViewModel
import com.henry.nexus.feature.news.ui.viewmodel.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    // Data
    single<NewsDataSource> { MockNewsDataSource() }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl() }
    // ViewModel
    viewModel { NewsViewModel(get()) }
    viewModel { NewsDetailViewModel(get(), get()) }
}