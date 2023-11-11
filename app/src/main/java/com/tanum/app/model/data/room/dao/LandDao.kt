package com.tanum.app.model.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanum.app.model.data.LahanData

@Dao
interface LandDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLand(land: List<LahanData>)

    @Query("SELECT * FROM land ORDER BY updatedAt DESC")
    fun getAllLand(): PagingSource<Int, LahanData>

    @Query("DELETE FROM land")
    suspend fun deleteAll()
}