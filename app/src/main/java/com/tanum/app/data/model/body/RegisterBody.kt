package com.tanum.app.data.model.body

data class RegisterBody(
    val job: String,
    val email: String,
    val password: String,
    val fullName: String
)
