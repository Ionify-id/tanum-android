package com.tanum.app.model.data

import com.google.gson.annotations.SerializedName


data class Meta(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String
)
