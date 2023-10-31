package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class Meta(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null
)
