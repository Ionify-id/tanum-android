package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class ProfileData(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("fullName")
    val fullName: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
