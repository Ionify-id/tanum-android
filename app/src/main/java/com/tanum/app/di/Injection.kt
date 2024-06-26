package com.tanum.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tanum.app.model.UserPreference
import com.tanum.app.model.data.room.TanumDatabase
import com.tanum.app.model.remote.retrofit.RetrofitConfig
import com.tanum.app.model.repository.ActivityRepository
import com.tanum.app.model.repository.ArticleRepository
import com.tanum.app.model.repository.LandRepository
import com.tanum.app.model.repository.UserRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection  {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = RetrofitConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideArticleRepository(): ArticleRepository {
        val apiService = RetrofitConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }

    fun provideLandRepository(context: Context): LandRepository {
        val apiService = RetrofitConfig.getApiService()
        val database = TanumDatabase.getDatabase(context)
        return LandRepository.getInstance(apiService, database)
    }

    fun provideActivityRepository(context: Context): ActivityRepository {
        val apiService = RetrofitConfig.getApiService()
        val database = TanumDatabase.getDatabase(context)
        return ActivityRepository.getInstance(apiService, database)
    }
}