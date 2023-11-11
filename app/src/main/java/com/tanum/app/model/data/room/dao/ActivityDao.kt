package com.tanum.app.model.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanum.app.model.data.AktivitasData

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activities: List<AktivitasData>)

    @Query("SELECT * FROM activity ORDER BY updatedAt DESC")
    fun getAllActivity(): PagingSource<Int, AktivitasData>

    @Query("DELETE FROM activity")
    suspend fun deleteAll()
}