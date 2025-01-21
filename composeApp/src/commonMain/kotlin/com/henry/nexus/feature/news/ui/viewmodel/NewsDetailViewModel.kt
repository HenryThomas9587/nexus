package com.henry.nexus.feature.news.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.nexus.feature.news.domain.repository.NewsRepository
import com.henry.nexus.feature.news.ui.state.NewsDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val newsRepository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(NewsDetailState())
    val state: StateFlow<NewsDetailState> = _state.asStateFlow()

    private var newsId = 0

    init {
        newsId = savedStateHandle.get<Int>("newsId")?:-1
        loadNewsDetail()
    }

    private fun loadNewsDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val newsDetail = newsRepository.getNewsDetail(newsId)
                _state.update {
                    it.copy(
                        newsDetail = newsDetail,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun retry() {
        loadNewsDetail()
    }
} 