package com.tanum.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanum.app.data.repository.ArticleRepository
import com.tanum.app.data.repository.UserRepository
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