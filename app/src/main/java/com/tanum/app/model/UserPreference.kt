package com.tanum.app.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tanum.app.model.data.ProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_VALUE] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_VALUE] ?: ""
        }
    }

    suspend fun saveProfile(profile: ProfileData) {
        dataStore.edit { preferences ->
            preferences[EMAIL_VALUE] = profile.email
            preferences[FULL_NAME_VALUE] = profile.fullName
            preferences[ID_VALUE] = profile.id
            preferences[CREATED_AT_VALUE] = profile.createdAt
            preferences[UPDATED_AT_VALUE] = profile.updatedAt
        }
    }

    suspend fun clearProfile() {
        dataStore.edit { preferences ->
            preferences[EMAIL_VALUE] = ""
            preferences[FULL_NAME_VALUE] = ""
            preferences[ID_VALUE] = 0
            preferences[CREATED_AT_VALUE] = ""
            preferences[UPDATED_AT_VALUE] = ""
        }
    }

    fun getProfile(): Flow<ProfileData> {
        return dataStore.data.map { preferences ->
            ProfileData(
                id = preferences[ID_VALUE] ?: 0,
                email = preferences[EMAIL_VALUE] ?: "",
                fullName = preferences[FULL_NAME_VALUE] ?: "",
                createdAt = preferences[CREATED_AT_VALUE] ?: "",
                updatedAt = preferences[UPDATED_AT_VALUE] ?: ""
            )
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val TOKEN_VALUE = stringPreferencesKey("token")
        private val ID_VALUE = intPreferencesKey("id")
        private val EMAIL_VALUE = stringPreferencesKey("email")
        private val FULL_NAME_VALUE = stringPreferencesKey("fullName")
        private val CREATED_AT_VALUE = stringPreferencesKey("createdAt")
        private val UPDATED_AT_VALUE = stringPreferencesKey("updatedAt")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}