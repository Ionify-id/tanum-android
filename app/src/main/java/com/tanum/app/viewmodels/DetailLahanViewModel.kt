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

    fun getDetailLand(
        id: Int,
        token: String
    ) = landRepository.getDetailLand(id, token)

    fun deleteLand(
        id: Int,
        token: String
    ) = landRepository.deleteLand(id, token)

    fun getAllActivities(
        landId: Int,
        token: String
    ) {

    }
}