package com.tanum.app.data.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lands")
data class LandEntity(
    @PrimaryKey
    val id: Int,
    val landName: String,
    val commodity: String,
    val variety: String,
    val dataStart: String
)