package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class VideoData(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)