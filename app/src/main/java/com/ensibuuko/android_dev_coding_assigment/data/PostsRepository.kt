package com.ensibuuko.android_dev_coding_assigment.data

import androidx.room.withTransaction
import com.ensibuuko.android_dev_coding_assigment.network.PostApi
import com.ensibuuko.android_dev_coding_assigment.util.networkBoundResource
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val api: PostApi,
    private val database: PostsDatabase,
) {
    private val postsDao = database.postsDao()

    fun getPosts() = networkBoundResource(
        query = {
            postsDao.getPosts()
        },
        fetch = {
            api.getPosts()
        },
        saveFetchResult = {
            database.withTransaction {
//                postsDao.deletePosts()
                postsDao.insertPosts(it)
            }
        }
    )
}