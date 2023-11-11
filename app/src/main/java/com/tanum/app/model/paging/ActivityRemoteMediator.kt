package com.tanum.app.model.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tanum.app.model.data.AktivitasData
import com.tanum.app.model.data.room.TanumDatabase
import com.tanum.app.model.data.room.entity.ActivityRemoteKeys
import com.tanum.app.model.remote.retrofit.ApiService

@OptIn(ExperimentalPagingApi::class)
class ActivityRemoteMediator(
    private val database: TanumDatabase,
    private val apiService: ApiService,
    private val token: String,
    private val landId: Int
): RemoteMediator<Int, AktivitasData>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AktivitasData>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.getListActivityByLand(landId, token, page, state.config.pageSize).data

            val endOfPaginationReached = responseData.activities.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.activityRemoteKeysDao().deleteRemoteKeys()
                    database.activityDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.activities.map {
                    ActivityRemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.activityRemoteKeysDao().insertAll(keys)
                database.activityDao().insertActivity(responseData.activities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AktivitasData>): ActivityRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.activityRemoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AktivitasData>): ActivityRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.activityRemoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AktivitasData>): ActivityRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.activityRemoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}