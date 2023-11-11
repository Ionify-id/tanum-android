package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.model.repository.ArticleRepository
import com.tanum.app.model.repository.UserRepository

class BeritaDetailViewModel(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
): ViewModel() {
    val token = userRepository.getToken()

    fun getArticleDetail(
        token: String,
        id: Int
    ) = articleRepository.getArticleDetail(token, id)
}