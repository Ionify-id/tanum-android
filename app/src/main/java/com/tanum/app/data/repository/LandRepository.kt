package com.tanum.app.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.tanum.app.data.model.LahanData
import com.tanum.app.data.model.body.LahanBody
import com.tanum.app.data.model.room.TanumDatabase
import com.tanum.app.data.paging.LandRemoteMediator
import com.tanum.app.data.remote.retrofit.ApiService
import com.tanum.app.utils.Result
import com.tanum.app.utils.handleCatchError
import com.tanum.app.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LandRepository(
    private val apiService: ApiService,
    private val landDatabase: TanumDatabase
) {

    fun createLand(
        token: String,
        lahanBody: LahanBody
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.createLand("Bearer $token", lahanBody)
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

    fun editLand(
        id: Int,
        token: String,
        lahanBody: LahanBody
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.editLand(id, "Bearer $token", lahanBody)
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

    fun getThreeLands(
        token: String
    ): LiveData<Result<ArrayList<LahanData>>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.getListLand("Bearer $token", 1, 3)
                if (response.meta.code == 200) {
                    emit(Result.Success(response.data))
                } else {
                    emit(Result.Error(response.meta.message))
                }
            } catch (e: Exception) {
                handleCatchError(e)
            }
        }
    }

    @ExperimentalPagingApi
    fun getAllLands(
        token: String
    ): LiveData<PagingData<LahanData>>{
        return Pager (
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            remoteMediator = LandRemoteMediator(landDatabase, apiService, "Bearer $token"),
            pagingSourceFactory = {
                landDatabase.landDao().getAllLand()
//                LandPagingSource(apiService, token)
            }
        ).liveData
    }

    fun getDetailLand(
        id: Int,
        token: String
    ): Flow<Result<LahanData>> = flow {
        Log.d("emit result", "loading")
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                Log.d("request response", "request")
                val response = apiService.getDetailLand(id, "Bearer $token")
                Log.d("after request", "request")
                if (response.meta.code == 200) {
                    Log.d("emit result", "loading")
                    emit(Result.Success(response.data))
                } else {
                    Log.d("emit result", "loading")
                    emit(Result.Error(response.meta.message))
                }
            } catch (e: Exception) {
                handleCatchError(e)
            }
        }
    }

    fun deleteLand(
        id: Int,
        token: String
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.deleteLand(id, "Bearer $token")
                val msg = response.data.message
                if (response.meta.code == 201) {
                    emit(Result.Success(msg))
                } else {
                    emit(Result.Success(msg))
                }
            } catch (e: Exception) {
                handleCatchError(e)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: LandRepository? = null
        fun getInstance(
            apiService: ApiService,
            landDatabase: TanumDatabase
        ): LandRepository =
            instance ?: synchronized(this) {
                instance ?: LandRepository(apiService, landDatabase)
            }.also {
                instance = it
            }
    }
}