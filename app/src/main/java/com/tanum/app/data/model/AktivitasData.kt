package com.tanum.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "activity")
data class AktivitasData(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("landId")
    val landId: Int,

    @field:SerializedName("cost")
    val cost: Int,

    @field:SerializedName("dateAction")
    val dateAction: String,

    @field:SerializedName("action")
    val action: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
): Parcelable