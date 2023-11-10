package com.tanum.app.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tanum.app.data.model.body.AktivitasBody
import com.tanum.app.data.remote.retrofit.ApiService
import com.tanum.app.utils.Result
import com.tanum.app.utils.handleCatchError
import com.tanum.app.utils.wrapEspressoIdlingResource

class ActivityRepository(
    private val apiService: ApiService
) {

    fun createActivity(
        landId: Int,
        token: String,
        aktivitasBody: AktivitasBody
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.createActivity(landId, "Bearer $token", aktivitasBody)
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

    fun editActivity(
        activityId: Int,
        token: String,
        aktivitasBody: AktivitasBody
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.editActivity(activityId, "Bearer $token", aktivitasBody)
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

    fun deleteActivity(
        activityId: Int,
        token: String,
        aktivitasBody: AktivitasBody
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.deleteActivity(activityId, "Bearer $token")
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

    /*
       need paging source
    */
    // TODO: 2. Make paging source for activity
    fun getAllActivity(){

    }

    companion object {
        @Volatile
        private var instance: ActivityRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ActivityRepository =
            instance ?: synchronized(this) {
                instance ?: ActivityRepository(apiService)
            }.also {
                instance = it
            }
    }
}