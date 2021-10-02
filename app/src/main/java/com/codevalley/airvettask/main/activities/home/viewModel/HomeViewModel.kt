package com.codevalley.airvettask.main.activities.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codevalley.airvettask.main.activities.home.repository.HomeDataSource
import com.codevalley.airvettask.main.activities.home.repository.HomeRepository
import com.codevalley.airvettask.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private var _userList: Flow<PagingData<Result>>? = null

    fun getUsers(): Flow<PagingData<Result>> {
        _userList = Pager(PagingConfig(pageSize = 10)) {
            HomeDataSource()
        }.flow
            .cachedIn(viewModelScope)
        return _userList as Flow<PagingData<Result>>
    }


    val allUsers: LiveData<List<Result>> = repository.allUsers.asLiveData()


    fun addUsers(users: List<Result>) = viewModelScope.launch {
        repository.addUsers(users)
    }

    fun deleteAllUsers() = viewModelScope.launch {
        repository.deleteAllUsers()
    }

}