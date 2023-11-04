package com.tanum.app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tanum.app.data.model.LahanData
import com.tanum.app.data.model.body.LahanBody
import com.tanum.app.data.remote.retrofit.ApiService
import com.tanum.app.utils.Result
import com.tanum.app.utils.handleCatchError
import com.tanum.app.utils.wrapEspressoIdlingResource

class LandRepository(
    private val apiService: ApiService
) {

    fun createLand(
        token: String,
        lahanBody: LahanBody
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.createLand(token, lahanBody)
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
                val response = apiService.editLand(id, token, lahanBody)
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
                val response = apiService.getListLahan("Bearer $token", 1, 3)
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

    /*
        need paging source
     */
    // TODO: 1. Make paging source for activity
    fun getAllLands(){

    }

    fun deleteLand(
        id: Int,
        token: String
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.deleteLahan(id, "Bearer $token")
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
            apiService: ApiService
        ): LandRepository =
            instance ?: synchronized(this) {
                instance ?: LandRepository(apiService)
            }.also {
                instance = it
            }
    }
}