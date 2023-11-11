package com.tanum.app.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.tanum.app.data.model.AktivitasData
import com.tanum.app.data.model.body.AktivitasBody
import com.tanum.app.data.model.room.TanumDatabase
import com.tanum.app.data.paging.ActivityRemoteMediator
import com.tanum.app.data.remote.response.Activity
import com.tanum.app.data.remote.retrofit.ApiService
import com.tanum.app.utils.Result
import com.tanum.app.utils.handleCatchError
import com.tanum.app.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ActivityRepository(
    private val apiService: ApiService,
    private val tanumDatabase: TanumDatabase
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

    @OptIn(ExperimentalPagingApi::class)
    fun getAllActivity(
        landId: Int,
        token: String
    ): LiveData<PagingData<AktivitasData>>{
        return Pager (
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            remoteMediator = ActivityRemoteMediator(tanumDatabase, apiService, "Bearer $token", landId),
            pagingSourceFactory = {
                tanumDatabase.activityDao().getAllActivity()
            }
        ).liveData
    }

    fun getDetailActivity(
        activityId: Int,
        token: String
    ): Flow<Result<Activity>> = flow {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.getDetailActivities(activityId, "Bearer $token")
                if (response.meta.code == 200) {
                    emit(Result.Success(response.data.activity))
                } else {
                    emit(Result.Error(response.meta.message))
                }
            } catch (e: Exception) {
                handleCatchError(e)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ActivityRepository? = null
        fun getInstance(
            apiService: ApiService,
            tanumDatabase: TanumDatabase
        ): ActivityRepository =
            instance ?: synchronized(this) {
                instance ?: ActivityRepository(apiService, tanumDatabase)
            }.also {
                instance = it
            }
    }
}