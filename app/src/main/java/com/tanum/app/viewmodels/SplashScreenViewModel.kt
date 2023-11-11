package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.model.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SplashScreenViewModel(userRepository: UserRepository): ViewModel() {
    val token: Flow<String> = userRepository.getToken()
}