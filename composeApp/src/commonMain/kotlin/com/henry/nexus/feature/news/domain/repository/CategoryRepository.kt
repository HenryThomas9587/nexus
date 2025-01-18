package com.henry.nexus.feature.news.domain.repository

import com.henry.nexus.feature.news.domain.model.CategoryModel

interface CategoryRepository {

    suspend fun getCategories(): List<CategoryModel>

    suspend fun getRecommendedCategories(page: Int, pageSize: Int): List<CategoryModel>

    suspend fun getCategoryById(id: Int): CategoryModel?
}