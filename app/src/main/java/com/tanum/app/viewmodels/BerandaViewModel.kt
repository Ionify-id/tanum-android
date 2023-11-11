package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.model.repository.ArticleRepository
import com.tanum.app.model.repository.LandRepository
import com.tanum.app.model.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class BerandaViewModel(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val landRepository: LandRepository
) : ViewModel() {
    val token: Flow<String> = userRepository.getToken()

    fun getThreeArticles(
        token: String
    ) = articleRepository.getThreeArticles(token)

    fun getThreeLands(
        token: String
    ) = landRepository.getThreeLands(token)
}