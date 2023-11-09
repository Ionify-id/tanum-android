package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.data.repository.LandRepository
import com.tanum.app.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DetailLahanViewModel(
    private val userRepository: UserRepository,
    private val landRepository: LandRepository
): ViewModel() {
    val token: Flow<String> = userRepository.getToken()

    fun deleteLand(
        token: String,
        id: Int
    ) = landRepository.deleteLand(id, token)

    fun getAllActivities(
        token: String,
        landId: Int
    ) {

    }
}