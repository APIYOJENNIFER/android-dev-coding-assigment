package com.ensibuuko.android_dev_coding_assigment.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comments_table")
    fun getComments(): Flow<List<Comments>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<Comments>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comments: Comments)

    @Update
    suspend fun update(comments: Comments)

    @Delete
    suspend fun delete(comments: Comments)
}