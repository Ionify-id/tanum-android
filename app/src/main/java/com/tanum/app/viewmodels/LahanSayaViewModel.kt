package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.tanum.app.model.repository.LandRepository
import com.tanum.app.model.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class LahanSayaViewModel(
    private val userRepository: UserRepository,
    private val landRepository: LandRepository
) : ViewModel() {
    val token: Flow<String> = userRepository.getToken()

    @OptIn(ExperimentalPagingApi::class)
    fun getAllLand(
        token: String
    ) = landRepository.getAllLands(token).cachedIn(viewModelScope)
}