package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName


data class Meta(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String
)
