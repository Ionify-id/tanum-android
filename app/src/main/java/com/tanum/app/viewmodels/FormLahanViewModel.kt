package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.model.data.body.LahanBody
import com.tanum.app.model.repository.LandRepository
import com.tanum.app.model.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class FormLahanViewModel(
    private val userRepository: UserRepository,
    private val landRepository: LandRepository
): ViewModel() {
    val token: Flow<String> = userRepository.getToken()

    fun createLand(
        token: String,
        landBody: LahanBody
    ) = landRepository.createLand(token, landBody)

    fun editLand(
        id: Int,
        token: String,
        landBody: LahanBody
    ) = landRepository.editLand(id, token, landBody)

    fun getDetailLand(
        id: Int,
        token: String
    ) = landRepository.getDetailLand(id, token)
}