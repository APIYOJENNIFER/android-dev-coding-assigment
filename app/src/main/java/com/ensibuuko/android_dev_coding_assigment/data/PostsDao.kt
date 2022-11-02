package com.ensibuuko.android_dev_coding_assigment.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts_table")
    fun getPosts(): Flow<List<Posts>>

    @Query("DELETE FROM posts_table")
    suspend fun deletePosts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Posts>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: Posts)

    @Update
    suspend fun update(posts: Posts)

    @Delete
    suspend fun delete(posts: Posts)
}