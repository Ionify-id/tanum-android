package com.tanum.app.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "land")
@Parcelize
data class LahanData(

    @field:SerializedName("area")
    val area: Float,

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

    @field:SerializedName("plant")
    val plant: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("profit")
    val profit: Int,

    @field:SerializedName("totalCost")
    val totalCost: Int,

    @field:SerializedName("updatedAt")
    val updatedAt: String
): Parcelable