package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.model.repository.UserRepository

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    fun postLogin(
        email: String,
        password: String
    ) = userRepository.postLogin(email, password)
}