package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class LahanData(

    @field:SerializedName("area")
    val area: Int? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("varietas")
    val varietas: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("ownership")
    val ownership: String? = null,

    @field:SerializedName("dateStart")
    val dateStart: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("profit")
    val profit: Int? = null,

    @field:SerializedName("totalCost")
    val totalCost: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)