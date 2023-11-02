package com.tanum.app.data.remote.response

data class ErrorResponse (
    val message: String = "",
    val stack: String = "",
    val status: Boolean = false
)