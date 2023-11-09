package com.tanum.app.data.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tanum.app.data.model.LahanData
import com.tanum.app.data.model.room.dao.LandDao
import com.tanum.app.data.model.room.dao.LandRemoteKeysDao
import com.tanum.app.data.model.room.entity.LandEntity
import com.tanum.app.data.model.room.entity.LandRemoteKeys

@Database(
    entities = [LahanData::class, LandRemoteKeys::class],
    version = 4,
    exportSchema = false
)
abstract class TanumDatabase: RoomDatabase() {

    abstract fun landDao(): LandDao
    abstract fun landRemoteKeysDao(): LandRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: TanumDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TanumDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TanumDatabase::class.java, "tanum_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}