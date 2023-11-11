package com.tanum.app.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tanum.app.model.UserPreference
import com.tanum.app.model.data.ProfileData
import com.tanum.app.model.data.body.LoginBody
import com.tanum.app.model.data.body.RegisterBody
import com.tanum.app.model.remote.retrofit.ApiService
import com.tanum.app.utils.Result
import com.tanum.app.utils.handleCatchError
import com.tanum.app.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun postLogin(
        email: String,
        password: String
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.login(LoginBody(email, password))
                val token = response.data.token
                userPreference.saveToken(token)
                Log.d("Success", token)

                val profile = apiService.getUserProfile("Bearer $token")
                saveProfile(profile.data)

                emit(Result.Success(response.meta.message))
            } catch (e: Exception) {
                handleCatchError(e)
            }
        }
    }

    fun postRegister(
        job: String,
        email: String,
        password: String,
        fullName: String
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.register(RegisterBody(job, email, password, fullName))
                val msg = response.meta.message
                if (response.meta.code == 201) {
                    emit(Result.Success(msg))
                } else {
                    emit(Result.Error(msg))
                }
            } catch (e: Exception) {
                handleCatchError(e)
            }
        }
    }

    private suspend fun saveProfile(profile: ProfileData) {
        userPreference.saveProfile(profile)
    }

    fun getProfileDetail(): Flow<ProfileData> {
        return userPreference.getProfile()
    }

    suspend fun logout() {
        userPreference.apply {
            clearProfile()
            saveToken("")
        }
    }

    fun getToken(): Flow<String> {
        return userPreference.getToken()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref)
            }.also {
                instance = it
            }
    }
}