package com.tanum.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tanum.app.data.UserPreference
import com.tanum.app.data.remote.retrofit.RetrofitConfig
import com.tanum.app.data.repository.UserRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection  {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = RetrofitConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, pref)
    }
}