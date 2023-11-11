package com.tanum.app.model.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "land_remote_keys")
data class LandRemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
