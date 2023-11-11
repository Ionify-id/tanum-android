package com.tanum.app.model.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanum.app.model.data.room.entity.ActivityRemoteKeys

@Dao
interface ActivityRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ActivityRemoteKeys>)

    @Query("SELECT * FROM activity_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: Int): ActivityRemoteKeys?

    @Query("DELETE FROM activity_remote_keys")
    suspend fun deleteRemoteKeys()
}