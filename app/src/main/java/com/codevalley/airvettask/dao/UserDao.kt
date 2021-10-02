package com.codevalley.airvettask.dao

import androidx.room.*
import com.codevalley.airvettask.models.Result
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM userData")
    fun getUsers(): Flow<List<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllUsers(users: List<Result>)

    @Query("DELETE FROM userData")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(result: Result)
}
