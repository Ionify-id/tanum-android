package com.tanum.app.model.data.body

data class RegisterBody(
    val job: String,
    val email: String,
    val password: String,
    val fullName: String
)
