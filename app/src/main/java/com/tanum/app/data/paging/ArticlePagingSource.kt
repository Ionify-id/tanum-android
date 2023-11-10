package com.tanum.app.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tanum.app.data.remote.response.ArticleListItem
import com.tanum.app.data.remote.retrofit.ApiService

class ArticlePagingSource(
    private val apiService: ApiService,
    private val token: String
) : PagingSource<Int, ArticleListItem>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleListItem> {
        return try {

            val position = params.key ?: INITIAL_PAGE_INDEX

            val responseData = apiService.getListArticles(token, position, params.loadSize).data

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}