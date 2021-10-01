package com.codevalley.airvettask.main.activities.home.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codevalley.airvettask.models.Result
import com.codevalley.airvettask.utils.RetrofitClient

class HomeDataSource : PagingSource<Int, Result>() {
    private val apiService = RetrofitClient.apiInterface

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getUsers("20", currentLoadingPageKey.toString())
            val responseData = mutableListOf<Result>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}