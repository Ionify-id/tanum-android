package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tanum.app.model.repository.ActivityRepository
import com.tanum.app.model.repository.LandRepository
import com.tanum.app.model.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DetailLahanViewModel(
    private val userRepository: UserRepository,
    private val landRepository: LandRepository,
    private val activityRepository: ActivityRepository
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
    ) = activityRepository.getAllActivity(landId, token).cachedIn(viewModelScope)

    fun deleteActivity(
        id: Int,
        token: String
    ) = activityRepository.deleteActivity(id, token)
}