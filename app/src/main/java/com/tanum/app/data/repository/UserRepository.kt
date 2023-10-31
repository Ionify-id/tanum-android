package com.tanum.app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tanum.app.data.AuthPreferences
import com.tanum.app.data.model.body.LoginBody
import com.tanum.app.data.model.body.RegisterBody
import com.tanum.app.data.remote.retrofit.ApiService
import com.tanum.app.utils.Result
import com.tanum.app.utils.convertErrorResponse
import com.tanum.app.utils.wrapEspressoIdlingResource
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) {

    fun postLogin(
        email: String,
        password: String
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.login(LoginBody(email, password))

                if (response.data != null) {
                    authPreferences.setToken(response.data.token)
                    emit(Result.Success(response.message))
                } else {
                    emit(Result.Error(response.message))
                }
            } catch (e: Exception) {
                when(e) {
                    is HttpException -> {
                        val jsonRes = convertErrorResponse(e.response()?.errorBody()?.string())
                        val msg =jsonRes.message
                        emit(Result.Error(msg))
                    }
                    else -> {
                        emit(Result.Error(e.message.toString()))
                    }
                }
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
                if (response.status) {
                    emit(Result.Success(response.message))
                } else {
                    emit(Result.Error(response.message))
                }
            } catch (e: Exception) {
                when(e) {
                    is HttpException -> {
                        val jsonRes = convertErrorResponse(e.response()?.errorBody()?.string())
                        val msg =jsonRes.message
                        emit(Result.Error(msg))
                    }
                    else -> {
                        emit(Result.Error(e.message.toString()))
                    }
                }
            }
        }
    }

    fun logout() {
        authPreferences.clearToken()
    }

//      need to edit authPref
//    fun getProfileDetail(): LiveData<ProfileData> = liveData {
//
//    }

}