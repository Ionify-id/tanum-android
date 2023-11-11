package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.model.repository.ArticleRepository
import com.tanum.app.model.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ArtikelViewModel(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    val token: Flow<String> = userRepository.getToken()

    fun getThreeArticles(
        token: String
    ) = articleRepository.getThreeArticles(token)

    fun getThreeVideos(
        token: String
    ) = articleRepository.getThreeVideos(token)

}