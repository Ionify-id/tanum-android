package com.tanum.app.model.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tanum.app.model.data.AktivitasData
import com.tanum.app.model.data.LahanData
import com.tanum.app.model.data.room.dao.ActivityDao
import com.tanum.app.model.data.room.dao.ActivityRemoteKeysDao
import com.tanum.app.model.data.room.dao.LandDao
import com.tanum.app.model.data.room.dao.LandRemoteKeysDao
import com.tanum.app.model.data.room.entity.ActivityRemoteKeys
import com.tanum.app.model.data.room.entity.LandRemoteKeys

@Database(
    entities = [LahanData::class, LandRemoteKeys::class, AktivitasData::class, ActivityRemoteKeys::class],
    version = 5,
    exportSchema = false
)
abstract class TanumDatabase: RoomDatabase() {

    abstract fun landDao(): LandDao
    abstract fun landRemoteKeysDao(): LandRemoteKeysDao
    abstract fun activityDao(): ActivityDao
    abstract fun activityRemoteKeysDao(): ActivityRemoteKeysDao

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