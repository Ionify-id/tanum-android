package com.tanum.app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tanum.app.data.model.LahanData
import com.tanum.app.data.remote.retrofit.ApiService

class LandPagingSource(
    private val apiService: ApiService,
    private val token: String
): PagingSource<Int, LahanData>() {
    override fun getRefreshKey(state: PagingState<Int, LahanData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LahanData> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getListLand("Bearer $token", position, params.loadSize).data
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}