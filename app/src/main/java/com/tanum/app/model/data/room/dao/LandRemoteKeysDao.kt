package com.tanum.app.model.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanum.app.model.data.room.entity.LandRemoteKeys

@Dao
interface LandRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<LandRemoteKeys>)

    @Query("SELECT * FROM land_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: Int): LandRemoteKeys?

    @Query("DELETE FROM land_remote_keys")
    suspend fun deleteRemoteKeys()
}