package com.tanum.app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.tanum.app.data.model.ArtikelData
import com.tanum.app.data.model.VideoData
import com.tanum.app.data.paging.ArticlePagingSource
import com.tanum.app.data.paging.VideoPagingSource
import com.tanum.app.data.remote.response.ArticleListItem
import com.tanum.app.data.remote.retrofit.ApiService
import com.tanum.app.utils.Result
import com.tanum.app.utils.convertErrorResponse
import com.tanum.app.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class ArticleRepository(
    private val apiService: ApiService
) {

    /*
        Article
     */

    fun getThreeArticles(
        token: String
    ): LiveData<Result<ArrayList<ArticleListItem>>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.getListArticles("Bearer $token", 1, 3)
                if (response.meta.code == 200) {
                    emit(Result.Success(response.data))
                } else {
                    emit(Result.Error(response.meta.message))
                }
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val jsonRes = convertErrorResponse(e.response()?.errorBody()?.string())
                        val msg = jsonRes.message
                        emit(Result.Error(msg))
                    }
                    else -> {
                        emit(Result.Error(e.message.toString()))
                    }
                }
            }
        }
    }

    // TODO: 2. implement infinite articles
    fun getAllArticles(
        token: String
    ): LiveData<PagingData<ArticleListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3
            ),
            pagingSourceFactory = {
                ArticlePagingSource(apiService, token)
            }
        ).liveData
    }

    fun getArticleDetail(
        token: String,
        articleId: Int
    ): Flow<Result<ArtikelData>> = flow {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.getArticleDetail("Bearer $token", articleId)
                if (response.meta.code == 200) {
                    emit(Result.Success(response.data))
                } else {
                    emit(Result.Error(response.meta.message))
                }
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val jsonRes = convertErrorResponse(e.response()?.errorBody()?.string())
                        val msg = jsonRes.message
                        emit(Result.Error(msg))
                    }
                    else -> {
                        emit(Result.Error(e.message.toString()))
                    }
                }
            }
        }
    }

    /*
        Video
     */

    fun getThreeVideos(
        token: String
    ): LiveData<Result<ArrayList<VideoData>>> = liveData {
        emit(Result.Loading)

        wrapEspressoIdlingResource {
            try {
                val response = apiService.getListVideo("Bearer $token", 1, 3)
                if (response.meta.code == 200) {
                    emit(Result.Success(response.data))
                } else {
                    emit(Result.Error(response.meta.message))
                }
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val jsonRes = convertErrorResponse(e.response()?.errorBody()?.string())
                        val msg = jsonRes.message
                        emit(Result.Error(msg))
                    }
                    else -> {
                        emit(Result.Error(e.message.toString()))
                    }
                }
            }
        }
    }

    // TODO: 3. implement infitinite videos
    fun getAllVideos(
        token: String
    ): LiveData<PagingData<VideoData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3
            ),
            pagingSourceFactory = {
                VideoPagingSource(apiService, token)
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService)
            }.also {
                instance = it
            }
    }

}