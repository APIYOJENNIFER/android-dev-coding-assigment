package com.ensibuuko.android_dev_coding_assigment.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Posts::class, Comments::class], version = 1)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
    abstract fun commentsDao(): CommentsDao
}