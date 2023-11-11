package com.tanum.app.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.tanum.app.model.data.ArtikelData
import com.tanum.app.model.data.VideoData
import com.tanum.app.model.paging.ArticlePagingSource
import com.tanum.app.model.paging.VideoPagingSource
import com.tanum.app.model.remote.response.ArticleListItem
import com.tanum.app.model.remote.retrofit.ApiService
import com.tanum.app.utils.Result
import com.tanum.app.utils.handleCatchError
import com.tanum.app.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
                handleCatchError(e)
            }
        }
    }

    fun getAllArticles(
        token: String
    ): LiveData<PagingData<ArticleListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                ArticlePagingSource(apiService, "Bearer $token")
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
                handleCatchError(e)
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
                handleCatchError(e)
            }
        }
    }

    fun getAllVideos(
        token: String
    ): LiveData<PagingData<VideoData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                VideoPagingSource(apiService, "Bearer $token")
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