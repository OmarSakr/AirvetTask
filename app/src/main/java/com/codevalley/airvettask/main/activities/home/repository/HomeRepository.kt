package com.codevalley.airvettask.main.activities.home.repository

import androidx.annotation.WorkerThread
import com.codevalley.airvettask.dao.UserDao
import com.codevalley.airvettask.models.Result
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val articleDao: UserDao) {
    val allUsers: Flow<List<Result>> = articleDao.getUsers()

    @WorkerThread
    suspend fun addUsers(users: List<Result>) {
        articleDao.saveAllUsers(users)
    }


    @WorkerThread
    suspend fun deleteAllUsers() {
        articleDao.deleteAll()
    }



}