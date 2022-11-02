package com.ensibuuko.android_dev_coding_assigment.network

import com.ensibuuko.android_dev_coding_assigment.data.Comments
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentsApi {
    @GET("comments")
    suspend fun getComments(@Query("postId") id: Int): List<Comments>
}