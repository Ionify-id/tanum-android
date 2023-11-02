package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class ArtikelData(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("thumbnail")
    val thumbnail: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

