package com.tanum.app.data.model.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanum.app.data.model.LahanData
import com.tanum.app.data.model.room.entity.LandEntity

@Dao
interface LandDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLand(land: List<LahanData>)

    @Query("SELECT * FROM land ORDER BY createdAt DESC")
    fun getAllLand(): PagingSource<Int, LahanData>

    @Query("DELETE FROM land")
    suspend fun deleteAll()
}