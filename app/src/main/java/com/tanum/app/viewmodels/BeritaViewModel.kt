package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tanum.app.model.repository.ArticleRepository
import com.tanum.app.model.repository.UserRepository

class BeritaViewModel(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
): ViewModel() {
    val token = userRepository.getToken()

    fun getListArticle(
        token: String
    ) = articleRepository.getAllArticles(token).cachedIn(viewModelScope)
}