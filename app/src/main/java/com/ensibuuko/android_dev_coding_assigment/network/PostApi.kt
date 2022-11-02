package com.ensibuuko.android_dev_coding_assigment.network.posts

import com.ensibuuko.android_dev_coding_assigment.data.Posts
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    suspend fun getPosts():List<Posts>
}