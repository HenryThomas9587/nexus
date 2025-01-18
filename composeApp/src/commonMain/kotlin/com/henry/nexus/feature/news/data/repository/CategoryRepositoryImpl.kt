package com.henry.nexus.feature.news.data.repository

import com.henry.nexus.feature.news.domain.model.CategoryModel
import com.henry.nexus.feature.news.domain.repository.CategoryRepository

class CategoryRepositoryImpl : CategoryRepository {
    override suspend fun getCategories(): List<CategoryModel> {
        return listOf()
    }

    override suspend fun getRecommendedCategories(page: Int, pageSize: Int): List<CategoryModel> {
        return listOf()
    }

    override suspend fun getCategoryById(id: Int): CategoryModel? {
        return null
    }
}