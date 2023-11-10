package com.tanum.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanum.app.data.remote.response.ArticleListItem
import com.tanum.app.data.repository.ArticleRepository
import com.tanum.app.data.repository.LandRepository
import com.tanum.app.data.repository.UserRepository
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