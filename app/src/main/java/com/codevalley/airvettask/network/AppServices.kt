package com.codevalley.airvettask.network

import com.codevalley.airvettask.models.UsersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AppServices {
    @GET(AppUrls.users)
    suspend fun getUsers(
        @Query("results") results: String,
        @Query("page") page: String
    ): Response<UsersModel>

}