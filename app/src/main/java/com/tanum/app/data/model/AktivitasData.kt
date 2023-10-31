package com.tanum.app.data.model

import com.google.gson.annotations.SerializedName

data class AktivitasData(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("landId")
    val landId: Int? = null,

    @field:SerializedName("cost")
    val cost: Int? = null,

    @field:SerializedName("dateAction")
    val dateAction: String? = null,

    @field:SerializedName("action")
    val action: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("category")
    val category: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
