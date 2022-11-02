package com.ensibuuko.android_dev_coding_assigment.network

import com.ensibuuko.android_dev_coding_assigment.data.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User
}