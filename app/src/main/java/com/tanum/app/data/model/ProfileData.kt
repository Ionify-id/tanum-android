package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class ProfileData(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("fullName")
    val fullName: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)
