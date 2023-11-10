package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.data.model.body.AktivitasBody
import com.tanum.app.data.repository.ActivityRepository
import com.tanum.app.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class FormAktivitasViewModel(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository
): ViewModel() {
    val token: Flow<String> = userRepository.getToken()

    fun createActivity(
        landId: Int,
        token: String,
        activityBody: AktivitasBody
    ) = activityRepository.createActivity(landId, token, activityBody)

    fun editActivity(
        activityId: Int,
        token: String,
        activityBody: AktivitasBody
    ) = activityRepository.editActivity(activityId, token, activityBody)
}