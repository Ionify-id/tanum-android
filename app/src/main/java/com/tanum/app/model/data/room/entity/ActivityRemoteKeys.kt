package com.tanum.app.model.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_remote_keys")
data class ActivityRemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)