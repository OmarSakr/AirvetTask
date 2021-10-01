package com.codevalley.airvettask.main.activities.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codevalley.airvettask.main.activities.home.repository.HomeDataSource
import com.codevalley.airvettask.models.Result
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {
    private var _userList: Flow<PagingData<Result>>? = null

    fun getUsers(): Flow<PagingData<Result>> {
        _userList = Pager(PagingConfig(pageSize = 10)) {
            HomeDataSource()
        }.flow
            .cachedIn(viewModelScope)
        return _userList as Flow<PagingData<Result>>
    }
}