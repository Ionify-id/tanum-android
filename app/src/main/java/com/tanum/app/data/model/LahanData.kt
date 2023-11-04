package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class LahanData(

    @field:SerializedName("area")
    val area: Int,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("varietas")
    val varietas: String,

    @field:SerializedName("userId")
    val userId: Int,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("ownership")
    val ownership: String,

    @field:SerializedName("dateStart")
    val dateStart: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("profit")
    val profit: Int,

    @field:SerializedName("totalCost")
    val totalCost: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)