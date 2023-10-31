package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class VideoData(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)