package com.tanum.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.tanum.app.data.repository.LandRepository
import com.tanum.app.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class LahanSayaViewModel(
    private val userRepository: UserRepository,
    private val landRepository: LandRepository
) : ViewModel() {
    val token: Flow<String> = userRepository.getToken()

    @OptIn(ExperimentalPagingApi::class)
    fun getAllLand(
        token: String
    ) = landRepository.getAllLands(token)
}