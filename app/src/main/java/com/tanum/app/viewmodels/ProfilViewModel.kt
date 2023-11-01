package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanum.app.data.model.ProfileData
import com.tanum.app.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ProfilViewModel(private val userRepository: UserRepository) : ViewModel() {
    val profile: Flow<ProfileData> = userRepository.getProfileDetail()
    fun logout() = viewModelScope.launch {
        userRepository.logout()
    }
}