package com.tanum.app.viewmodels

import androidx.lifecycle.ViewModel
import com.tanum.app.model.repository.UserRepository

class RegisterViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    fun postRegister(
        job: String,
        email: String,
        password: String,
        fullName: String
    ) = userRepository.postRegister(job, email, password, fullName)

}